#include <stdio.h>
#include <omp.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <iomanip>
#include <fstream>

using namespace std;

int m1r;
int m1c;
int m2r;
int m2c;

double** parseMatrix(char fileP[]) {
    ifstream file(fileP);

    int rows;
    int cols;

    file >> rows >> cols;

    file.ignore(100000, '\n');

    if (m1r == 0) {
        m1r = rows;
        m1c = cols;
    } else {
        m2r = rows;
        m2c = cols;
    }

    double** matrix = new double*[rows];

    for (int i = 0; i < rows; i++) {
        matrix[i] = new double[cols];
    }

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            file >> matrix[i][j];
        }
        file.ignore(100000, '\n');
    }

    return matrix;
}

void printMatrix(double** matrix, int rows, int cols) {
    cout << fixed << setprecision(6);

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
}

void MatrixMult(char file1[], char file2[], int T)
{
    omp_set_num_threads(T);

    double** matrix1 = parseMatrix(file1);
    double** matrix2 = parseMatrix(file2);

    double** result = new double*[m1r];

    #pragma omp parallel for
    for (int i = 0; i < m1r; i++) {
        result[i] = new double[m2c];
    }

    #pragma omp parallel for
    for (int i = 0; i < m1r; i++) {
        #pragma omp parallel for
        for (int j = 0; j < m2c; j++) {
            int sum = 0;
            #pragma omp parallel for reduction (+:sum)
            for (int k = 0; k < m1c; k++) {
                sum += matrix1[i][k] * matrix2[k][j];
            }
            result[i][j] = sum;
        }
    }

    cout << m1r << " " << m2c << endl;
    printMatrix(result, m1r, m2c);

    #pragma omp parallel for
    for (int i = 0; i < m1r; i++) {
        delete[] matrix1[i];
    }

    #pragma omp parallel for
    for (int i = 0; i < m2r; i++) {
        delete[] matrix2[i];
    }

    #pragma omp parallel for
    for (int i = 0; i < m1r; i++) {
        delete[] result[i];
    }

    delete[] matrix1;
    delete[] matrix2;
    delete[] result;
}

int main(int argc, char *argv[])
{
    char *file1, *file2;
    file1 = argv[1];
    file2 = argv[2];
    int T = atoi(argv[3]);

    MatrixMult(file1, file2, T);

    return 0;
}
