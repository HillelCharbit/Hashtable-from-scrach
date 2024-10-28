

# HashTable from scratch

This repository provides Java implementations of two classic hash table approaches, each optimized for handling collisions differently. The project is modular, allowing for customization of hash functions and thorough performance testing.

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [How to Use](#how-to-use)
- [Additional Notes](#additional-notes)
- [Contributing](#contributing)

## Overview

This project contains two primary hash table implementations:

- **ChainedHashTable**: Uses separate chaining to handle collisions. Each bucket is a linked list, with new key-value pairs added to the list when collisions occur.
  
- **ProbingHashTable**: Uses open addressing with linear probing. Collisions are handled by linearly probing to the next available bucket.

## Key Features

- **Common `HashTable` Interface**: Ensures consistent method signatures and functionality across all hash table implementations.
  
- **Modular Hash Function Design**:
  - `HashFactory` and `HashFunctor` interfaces allow for flexibility in hash function selection.
  - Included hash function generators: `ModularHash`, `MultiplicativeShiftingHash`, and `StringHash`, each suited for specific key types and use cases.

- **Utility Classes**:
  - `HashingExperimentUtils`: Measures the performance of hash tables, facilitating analysis of insertion and search operations under varying load factors.
  - `HashingUtils`: Provides helper methods for generating random data, simplifying test case creation.

## How to Use

1. Clone the repository:
   ```bash
   git clone https://github.com/HillelCharbit/Hashtable-from-scrach.git
   cd Hashtable-from-scrach
   ```

2. Compile the Java files:
   ```bash
   javac -d bin src/*.java
   ```

3. Run the main class:
   ```bash
   java -cp bin Main
   ```
   This will execute a basic test suite. For advanced performance testing, refer to the `HashingExperimentUtils` class.

## Additional Notes

- **Extensibility**: New hash table types or hash functions can be easily added.
- **Performance Testing**: `HashingExperimentUtils` provides options for benchmarking hash tables with different load factors and key types.
- **Test Data Generation**: `HashingUtils` offers tools for generating random numbers and strings to facilitate robust testing.

## Contributing

Contributions are welcome! To report a bug or suggest an improvement, please open an issue or submit a pull request.
