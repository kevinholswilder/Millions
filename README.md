# Millions

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Installation](#installation)
4. [App data](#app-data)
5. [Build](#build)
6. [Steps to run](#steps-to-run)
7. [Testing](#testing)
8. [Contributing](#contributing)
9. [License](#license)

## Overview
Millions is a stock market simulation game developed as a part of the course assignment given in _Programming 2 IDATT2003_ at the Norwegian University of Science and Technology in Trondheim. The application allows users to manage a virtual portfolio using a configurable starting capital and stock market data loaded from a chosen file. Players can buy and sell shares, track the market's history, manage their transactions and financial progress over time.

The game simulates weekly market changes, where stock prices flucatate as the user advances through each week. Users can analyze stock information and statistics such as a stock’s all-time high and low.

The purpose of this application is to provide a realistic and interactive environment for learning basic stock trading concepts.

## Features
* Start a new game with customizable starting capital
* Load stock market data from external files
* View stock information and statistics
* Buy and sell shares
* Track all completed transactions
* Display market winners and losers
* Advance the simulation week by week with updated stock prices
* Calculate portfolio value and player status
* Sell all holdings and exit the game safely

## Installation
You can install the application from the [Releases](../../releases) page on our GitHub.  
Download the version for your respective operating system and follow the included installation instructions, or download the `.jar` file if you prefer to run the application using Java.

## App data
Save game data and logs are stored at:
- Windows: `%APPDATA%\Millions\`
- macOS:   `~/Library/Application Support/Millions/`
- Linux:   `~/.local/share/Millions/`

**NOTE**: These files will persist after uninstalling the application. Manual removal is required for permanent deletion.

## Build
To build the application from the source, clone the repository by executing:

```
git clone https://github.com/kevinholswilder/Millions
```
After cloning you can build the application by executing the following command:

```
mvn clean package
```

## Steps to Run
After following the steps in [Installation](#installation), you can run the application in one of the following ways:

- If you downloaded the version for your operating system, launch the application by double-clicking the installed shortcut.
- If you downloaded the `.jar` version, execute the application by:
```
java -jar Millions-{version}.jar
```

## Testing
The application uses JUnit6 for unit testing. These tasks are ran by executing the following:
```
mvn clean test
```

## Contributing

Contributions to Millions are welcome. If you would like to improve the project, add new features, or fix bugs, follow the steps below:

1. Fork the repository
2. Create a new branch for your feature or fix

```
git checkout -b your-name/feature
```
3. Make your changes and test the application
4. Test that the changes work
5. Push your branch to your fork
6. Open a pull request describing your changes

## License
This project is licensed under the MIT License, see [MIT License](./LICENSE) for details.
