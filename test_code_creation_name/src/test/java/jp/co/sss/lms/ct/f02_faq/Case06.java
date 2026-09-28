package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver,
				java.time.Duration.ofSeconds(10));

		//「機能」を押下し、プルダウンメニューを開く
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.partialLinkText("機能")))
				.click();

		//プルダウン内の「ヘルプ」リンクを押下
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.partialLinkText("ヘルプ")))
				.click();

		//ページ遷移の待機
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.titleIs("ヘルプ | LMS"));

		//画面遷移後のタイトルの確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//現在のウィンドウのIDを取得
		String currentHandle = webDriver.getWindowHandle();

		//「よくある質問」リンクの押下
		webDriver.findElement(By.partialLinkText("よくある質問")).click();

		//新しく開いたタブに操作対象を切り替える
		for (String handle : webDriver.getWindowHandles()) {
			if (!handle.equals(currentHandle)) {
				webDriver.switchTo().window(handle);
				break;
			}
		}

		//ページ遷移の待機
		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver,
				java.time.Duration.ofSeconds(10));
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.titleIs("よくある質問 | LMS"));

		//画面遷移後のタイトルの確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver,
				java.time.Duration.ofSeconds(10));

		//【研修関係】のカテゴリリンクを押下
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(By.partialLinkText("研修関係")))
				.click();

		//URLにカテゴリIDが含まれるまで待機
		wait.until(
				org.openqa.selenium.support.ui.ExpectedConditions.urlContains("frequentlyAskedQuestionCategoryId=1"));

		//該当カテゴリの質問が1件以上存在することを確認
		var questions = webDriver.findElements(By.tagName("dt"));
		assertTrue(questions.size() > 0, "該当カテゴリの質問が1件以上存在すること");

		//Javascript実行用オブジェクトの準備
		org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) webDriver;

		//画面を一番下までスクロールする
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(webDriver,
				java.time.Duration.ofSeconds(10));

		//Javascript実行用オブジェクトの準備
		org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) webDriver;

		//該当カテゴリの質問が1件以上存在することを確認
		var dts = webDriver.findElements(By.tagName("dt"));
		assertTrue(dts.size() > 0, "検索結果の質問が1件以上存在すること");

		//検索結果を全件クリックし、回答部分を開く
		for (var dt : dts) {
			js.executeScript("arguments[0].click();", dt);
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//画面を一番下までスクロールする
		js.executeScript("window.scrollTo(0,document.body.scrollHeight);");

		//エビデンスの取得
		getEvidence(new Object() {
		});
	}

}
