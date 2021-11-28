# APIAutomationFramework

1) Clone project
  - cmd: git clone https://github.com/dglequang/APIAutomationFramework.git
2) Run a single test (Stay at project folder contain pom file)
  - cmd: mvn clean test "-Dkarate.options=--tags @feature1" -Dtest=TestRunner
3) Run all scenarios in feature file (Stay at project folder contain pom file)
  - cmd: mvn clean test -Dtest=TestRunner
4) View Cucumber report
- Go to folder: src/target/cucumber-html-reports
5) Open file: overview-features.html
- Change environment
6) cmd: mvn test -Dkarate.env=sit
