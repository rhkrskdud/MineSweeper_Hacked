
# 💣 Minesweeper_Hacked

> 자바 지뢰찾기 게임을 분석 및 수정하여, 실행 즉시 **지뢰 위치를 자동으로 표시**하도록 해킹한 프로젝트입니다.

---

## 🧩 프로젝트 개요

이 프로젝트는 인터넷에 공개된 Java 기반 **Minesweeper 게임의 JAR 파일을 디컴파일 및 수정**하여,  
프로그램 실행과 동시에 **모든 지뢰 위치를 미리 보여주는 기능**을 구현한 리버싱 실습 예제입니다.

---

## 🖥️ 원본 게임 실행 화면

<div align="center">
  <img src="https://github.com/user-attachments/assets/606ee2e5-a985-4eec-a8f9-cca0b4399ed5" width="350"/>
  <br/>
  <sub>기존 게임은 지뢰 위치를 알 수 없으며, 일반적인 플레이 방식으로 진행됩니다.</sub>
</div>

---

## 🔧 수정 후 실행 화면

<div align="center">
  <img src="https://github.com/user-attachments/assets/5c12df07-1ec3-4ddf-8081-64bbc2e445ef" width="350"/>
  <br/>
  <sub>수정된 버전은 실행하자마자 모든 지뢰 위치가 자동으로 깃발로 표시됩니다.</sub>
</div>


---

## 🛠️ 수정 방법 요약

1. 원본 `Minesweeper.jar` 압축 해제  
   ```bash
   jar xf Minesweeper.jar
   ```

2. 핵심 게임 로직이 담긴 `.class` 파일 디컴파일
   (예: `GamePanel.class` → JD-GUI / CFR / Fernflower 등으로)

3. 지뢰 위치를 저장하는 로직 분석 후,
   **모든 지뢰를 깃발로 표시하도록 코드 수정**

4. 수정한 Java 파일을 다시 컴파일

   ```bash
   javac -d . com/yourmodified/GamePanel.java
   ```

5. 새로운 JAR 파일 생성

   ```bash
   jar cmf META-INF/MANIFEST.MF HackedMinesweeper.jar com
   ```

6. 실행 테스트

   ```bash
   java -jar HackedMinesweeper.jar
   ```

---

## 🧠 학습 포인트

* `.jar` 구조 분석 및 압축 해제 방법
* `.class` 파일 디컴파일 → 수정 → 재컴파일
* Java GUI 프로그램의 내부 동작 구조 이해
* 보안 실습 환경에서의 클라이언트 조작 및 리버싱 흐름


---


## 👩‍💻 제작자

* 곽나영
