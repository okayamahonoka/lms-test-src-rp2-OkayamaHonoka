package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

import lombok.experimental.var;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//指定のURLに遷移する
		goTo("http://localhost:8080/lms");

		//画面遷移が行われたかどうかの確認
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//入力項目のクリアと各項目の入力(ログインID)
		var loginIdInput = webDriver.findElement(By.name("loginId"));
		loginIdInput.clear();
		loginIdInput.sendKeys("StudentAA01");

		//入力項目のクリアと各項目の入力(パスワード)
		var passwordInput = webDriver.findElement(By.name("password"));
		passwordInput.clear();
		passwordInput.sendKeys("Studenta01");

		//ログインボタンの押下
		webDriver.findElement(By.xpath("//input[@value='ログイン']")).click();

		//ページ遷移の待機
		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver,
				java.time.Duration.ofSeconds(10));
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.titleIs("コース詳細 | LMS"));

		//画面遷移後のタイトルの確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//要素が表示されるまで待機
		By detailBtnLocator = (By.xpath("//tr[contains(.,'未提出')]//input[@value='詳細']"));
		visibilityTimeout(detailBtnLocator, 10);

		//対象の要素を取得
		var detailBtn = webDriver.findElement(detailBtnLocator);

		//JavaScriptを使用して直接クリックを実行
		((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailBtn);

		//画面遷移後のタイトルの確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「日報を提出する」ボタンのロケータ定義
		By submitBtnLocator = By.xpath("//input[@value='日報【デモ】を提出する']");

		//ボタンが表示されるまで待機
		visibilityTimeout(submitBtnLocator, 10);

		//ボタンの取得とクリック
		var submitBtn = webDriver.findElement(submitBtnLocator);
		((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);

		//画面遷移後のタイトルの確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//日報の内容を入力
		var contentInput = webDriver.findElement(By.id("content_0"));
		contentInput.clear();
		contentInput.sendKeys("本日の研修内容と成果を入力します。");

		//「提出する」ボタンのロケータ定義とクリック
		By submitBtnLocator = By.xpath("//button[contains(text(),'提出する')]");
		visibilityTimeout(submitBtnLocator, 10);

		var submitBtn = webDriver.findElement(submitBtnLocator);
		((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);

		//セクション詳細画面へ遷移することを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//ボタン名が「提出済み日報【デモ】を確認する」に変更されていることを確認
		By confirmBtnLocator = By.xpath("//input[@value='提出済み日報【デモ】を確認する']");
		visibilityTimeout(confirmBtnLocator, 10);

		assertTrue(webDriver.findElement(confirmBtnLocator).isDisplayed());

		//エビデンスの取得
		getEvidence(new Object() {
		});

	}

}
