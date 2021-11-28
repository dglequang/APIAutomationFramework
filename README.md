# APIAutomationFramework

1) Clone project
  - cmd: git clone https://github.com/dglequang/APIAutomationFramework.git
2) Run a single scenario
  - cmd: mvn test -Dcucumber.filter.tags="@regression2"
3) Run all scenarios 
  - cmd: mvn test 
4) View Cucumber report
- Go to folder: src/target/reports/latest_datetime_folder/cucumber-html-reports
- Open file: overview-features.html
5) Change environment (Current: env=dev)
- cmd: mvn test -Denv=sit
