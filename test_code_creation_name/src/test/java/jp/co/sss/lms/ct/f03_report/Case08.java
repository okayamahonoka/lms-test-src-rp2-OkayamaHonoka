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
import org.openqa.selenium.JavascriptExecutor;

import lombok.experimental.var;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//要素が表示されるまで待機
		By detailBtnLocator = (By.xpath("//tr[contains(.,'提出済')]//input[@value='詳細']"));
		visibilityTimeout(detailBtnLocator, 10);

		//対象の要素を取得
		var detailBtn = webDriver.findElement(detailBtnLocator);

		//JavaScriptを使用して直接クリックを実行
		//((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailBtn);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailBtn);

		//画面遷移後のタイトルの確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//「提出済み日報を確認する」ボタンのロケータ定義
		By submitBtnLocator = By.xpath("//input[@value='提出済み日報【デモ】を確認する']");

		//ボタンが表示されるまで待機
		visibilityTimeout(submitBtnLocator, 10);

		//ボタンの取得とクリック
		var submitBtn = webDriver.findElement(submitBtnLocator);
		//((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);

		//画面遷移後のタイトルの確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		//日報の内容を入力
		var contentInput = webDriver.findElement(By.id("content_0"));
		contentInput.clear();
		contentInput.sendKeys("本日の研修内容と成果を修正入力します。");

		//「提出する」ボタンの表示待機
		By submitBtnLocator = By.xpath("//button[contains(text(),'提出する')]");
		visibilityTimeout(submitBtnLocator, 10);

		//ボタンの取得とクリック
		var submitBtn = webDriver.findElement(submitBtnLocator);
		//((org.openqa.selenium.JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitBtn);

		//セクション詳細画面へ遷移することを確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		//「ようこそ」のリンク指定と表示待機
		By userLinkLocator = By.xpath("//a[contains(@href,'/user/detail')]");
		visibilityTimeout(userLinkLocator, 10);

		//リンクの取得とクリック
		var userLink = webDriver.findElement(userLinkLocator);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", userLink);

		//ユーザー詳細画面へ遷移することを確認
		assertEquals("ユーザー詳細", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		//該当レポートの「詳細」ボタンの表示待機と押下
		By detailBtnLocator = By.xpath("(//input[@value='詳細'])[2]");
		visibilityTimeout(detailBtnLocator, 10);

		var detailBtn = webDriver.findElement(detailBtnLocator);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", detailBtn);

		//画面遷移後のタイトルの確認
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		//修正したテキストが画面上に表示されていることを確認
		By updateContentLocator = By.xpath("//*[contains(text(),'本日の研修内容と成果を修正入力します。')]");
		visibilityTimeout(updateContentLocator, 10);

		assertTrue(webDriver.findElement(updateContentLocator).isDisplayed());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
