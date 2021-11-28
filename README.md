# APIAutomationFramework

Clone project
- cmd: git clone https://github.com/dglequang/APIAutomation.git
Run a single test (Stay at project folder contain pom file)
- cmd: mvn clean test "-Dkarate.options=--tags @feature1" -Dtest=TestRunner
Run all scenarios in feature file (Stay at project folder contain pom file)
- cmd: mvn clean test -Dtest=TestRunner
View Cucumber report
- Go to folder: src/target/cucumber-html-reports
Open file: overview-features.html
- Change environment
cmd: mvn test -Dkarate.env=sit
