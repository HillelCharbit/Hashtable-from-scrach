HashTableImplementations
This repository contains Java implementations of two classic hash table approaches: separate chaining and open addressing with linear probing. The project focuses on providing efficient and correct implementations of these fundamental data structures, along with tools for performance analysis and experimentation.

Table of Contents
Features
Installation
Usage
Class Descriptions
Configuration
Performance Evaluation
Contributing
License
Features
ChainedHashTable: Implements a hash table with separate chaining for collision resolution. Each bucket in the hash table is a linked list, and collisions are handled by adding the new key-value pair to the list for the corresponding bucket.
ProbingHashTable: Implements a hash table with open addressing using linear probing for collision resolution. If a collision occurs, the algorithm linearly probes subsequent buckets until an empty bucket is found.
HashTable Interface: Both implementations adhere to a common HashTable interface, providing a consistent API for interacting with the hash tables.
HashFactory and HashFunctor: Provides a flexible mechanism for generating and defining hash functions, allowing for easy customization and experimentation with different hash functions.
ModularHash, MultiplicativeShiftingHash, and StringHash: Includes implementations of various hash function generators, offering a range of options for different key types and use cases.
HashingExperimentUtils: A utility class for conducting performance measurements and analysis of the hash table implementations under varying load factors.
HashingUtils: A utility class with helper methods for generating random numbers and strings, useful for creating test data.
Installation
Clone the repository:
bash
Copy code
git clone https://github.com/your-username/HashTableImplementations.git
Navigate to the project directory:
bash
Copy code
cd HashTableImplementations
Compile the Java files:
bash
Copy code
javac -d bin src/*.java
Usage
Run the Main class:
bash
Copy code
java -cp bin Main
Conduct performance experiments using HashingExperimentUtils:
java
Copy code
// Example usage in Main.java
HashingExperimentUtils.measureInsertionPerformance(new ChainedHashTable<>(new ModularHash()), 10000);
Class Descriptions
Hash Table Implementations
ChainedHashTable: Implements the HashTable interface using separate chaining for collision resolution.
ProbingHashTable: Implements the HashTable interface using open addressing with linear probing.
Utilities and Supporting Classes
HashFactory: An interface for generating hash functions.
HashFunctor: An interface for defining the hash function itself.
HashingExperimentUtils: A utility class for measuring the performance of hash table implementations.
HashingUtils: A utility class with helper methods for generating random data.
HashTable: An interface defining the common operations for hash tables (insert, search, delete).
ModularHash: Implements HashFactory using modular arithmetic.
MultiplicativeShiftingHash: Implements HashFactory using multiplicative shifting.
StringHash: Implements HashFactory specifically for string keys.
Configuration
Hash Function Selection: You can specify which hash function to use by modifying the HashFactory instance passed to your hash table.
Performance Evaluation
The HashingExperimentUtils class provides methods to measure the performance of the hash table implementations. You can use this class to analyze insertion and search times with different load factors and hash functions.

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.
Create a new branch:
bash
Copy code
git checkout -b feature/YourFeature
Commit your changes:
bash
Copy code
git commit -am 'Add YourFeature'
Push the branch:
bash
Copy code
git push origin feature/YourFeature
Open a Pull Request.
License
This project is licensed under the MIT License. See the LICENSE file for details.
