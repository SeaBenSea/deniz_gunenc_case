# Deniz Günenç – Selenium Java Case Study
![Java CI](https://github.com/SeaBenSea/deniz_gunenc_case/actions/workflows/ci.yml/badge.svg)

> Automated UI test suite built with **Java 22**, **Selenium 4**, and **TestNG**. The goal is to
> demonstrate modern Page Object Model (POM) practices, clean code, and a CI‑ready workflow.

---

## 📑 Table of Contents

1. [Project Overview](#project-overview)
2. [Tech Stack](#tech-stack)
3. [Directory Layout](#directory-layout)
4. [Prerequisites](#prerequisites)
5. [Getting Started](#getting-started)
6. [Running the Tests](#running-the-tests)
7. [Continuous Integration](#continuous-integration)
8. [Customising & Extending](#customising--extending)
9. [Contributing](#contributing)

---

## Project Overview

This repository automates the following end‑to‑end user journeys:

| # | Scenario                                                                                                              |
|---|-----------------------------------------------------------------------------------------------------------------------|
| 1 | Home page loads successfully                                                                                          |
| 2 | Navigating via the **Company › Careers** menu loads the Careers page and its key sections                             |
| 3 | The **See all QA jobs** CTA navigates to the Open Positions page with the `department=qualityassurance` URL parameter |
| 4 | Filtering by **Location = _Istanbul, Turkiye_** & **Department = _Quality Assurance_** shows only matching jobs       |
| 5 | Clicking **View Role** opens the Lever application form in a new tab                                                  |

Tests run both locally (headed / headless) and on CI (GitHub Actions) using Maven Wrapper.

---

## Tech Stack

| Area           | Technology                   | Notes                                            |
|----------------|------------------------------|--------------------------------------------------|
| Language       | **Java 22**                  | Modern JDK features enabled                      |
| Build          | **Maven 3 (wrapper)**        | No need to install Maven globally                |
| Automation     | **Selenium 4.25.0**          | W3C WebDriver                                    |
| Test Runner    | **TestNG 7.10.2**            | Parallel‑ready, flexible configuration           |
| Design Pattern | **Page Object Model (POM)**  | Reusable page abstractions                       |
| CI             | **GitHub Actions**           | See `.github/workflows/ci.yml`                   |
| Optional       | **Selenoid + browsers.json** | Run tests inside Dockerised Chrome 99 VNC images |

---

## Directory Layout

```
deniz_gunenc_case/
│  browsers.json            # Selenoid browser map (optional)
│  pom.xml                  # Maven POM & dependencies
│  testng.xml               # TestNG suite definition
│  mvnw* / .mvn/            # Maven Wrapper scripts
│
├─ src/main/java/pages/     # Page Objects (POM)
│      BasePage.java
│      HomePage.java
│      CareersPage.java
│      CareersQAPage.java
│      OpenPositionsQAPage.java
│
├─ src/test/java/tests/     # Test classes
│      BaseTest.java        # WebDriver setup/teardown
│      CaseStudyTest.java   # 5 scenarios
│
└─ .github/workflows/ci.yml # GitHub Actions pipeline
```

---

## Prerequisites

- **JDK 22** installed and `JAVA_HOME` configured  
  _Tip:On macOS you can run `brew install openjdk@22`_
- **Chrome** ≥ 99 locally **or** access to a Selenium grid (e.g. Selenoid)
- (Windows only) Download `chromedriver.exe` matching your Chrome version and add it to `PATH` or set the
  `webdriver.chrome.driver` system property.

> The Maven Wrapper (`./mvnw`/ `mvnw.cmd`) will download Maven 3.9.6 automatically – no manual installation required.

---

## Getting Started

```bash
# 1. Clone the repo
$ git clone https://github.com/SeaBenSea/deniz_gunenc_case.git
$ cd deniz_gunenc_case

# 2. Execute the full TestNG suite (headed)
$ ./mvnw test -DsuiteXmlFile=testng.xml

# 3. Execute headless (uses Chrome —headless=new)
$ ./mvnw test -DsuiteXmlFile=testng.xml -Dheadless=true
```

| Useful Flags                                      | Description                                         |
|---------------------------------------------------|-----------------------------------------------------|
| `-DsuiteXmlFile=<file>`                           | Point to a different TestNG suite                   |
| `-Dheadless=true`                                 | Force headless even on desktop                      |
| `-Dci=true`                                       | Applied automatically in CI to enable headless mode |
| `-Dwebdriver.chrome.driver=/path/to/chromedriver` | Override driver path if not in `PATH`               |

---

## Running the Tests

### Local Desktop (default)

1. Ensure Chrome and the matching **ChromeDriver** are installed.
2. Run `./mvnw test` as shown above – a browser window will appear.

### Headless

Add `-Dheadless=true` or export `CI=true`.

---

## Continuous Integration

GitHub Actions runs the suite on every **push** and **pull request** to `master` via the workflow defined in
`.github/workflows/ci.yml`:

```yaml
- JDK 22 is provisioned
- Maven dependencies are cached
- Tests run headless with `CI=true`
- Surefire reports are uploaded as a workflow artifact
```

---

## Customising & Extending

| Area                        | How‑to                                                                                                                                  |
|-----------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| **Add a new test**          | Create a Page Object under `src/main/java/pages`, then add a method in `CaseStudyTest` (or a new class) and include it in `testng.xml`. |
| **Change timeouts**         | Update `TIMEOUT` constant in `BasePage.java`.                                                                                           |

---

## Contributing

1. **Fork** the repo and create your feature branch: `git checkout -b feat/awesome`
2. **Commit** using [Conventional Commits](https://www.conventionalcommits.org/).
3. **Push** to the branch and open a **Pull Request**.
4. Ensure all tests and linter checks pass – the CI must be green.

> Please open an issue first to discuss major changes.

---
