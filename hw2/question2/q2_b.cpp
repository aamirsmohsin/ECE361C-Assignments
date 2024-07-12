#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>
#include <iostream>

using namespace std;

int Sieve(int N, int threads)
{
    omp_set_num_threads(threads);

    bool* isPrime = new bool[N + 1];

    #pragma omp parallel for num_threads(threads)
    for (int i = 0; i < N + 1; i++) {
        isPrime[i] = true;
    }

    isPrime[0] = false;
    isPrime[1] = false;

    int upper_bound = (int) ceil(sqrt(N));

    #pragma omp parallel for num_threads(threads)
    for (int i = 2; i < upper_bound + 1; i++) {
        if (isPrime[i] == true) {
            #pragma omp parallel for num_threads(threads)
            for (int j = i * i; j < N + 1; j += i) {
                isPrime[j] = false;
            }
        }
    }

    int res = 0;

    #pragma omp parallel for reduction (+:res) num_threads(threads)
    for (int i = 0; i < N + 1; i++) {
        if (isPrime[i]) res += 1;
    }

    delete[] isPrime;

    return res;
}

int main(void)
{
    int num_primes;
    int num_threads = 1;

    num_primes = Sieve(1000000, num_threads);

    cout << num_primes << endl;

    return 0;
}
