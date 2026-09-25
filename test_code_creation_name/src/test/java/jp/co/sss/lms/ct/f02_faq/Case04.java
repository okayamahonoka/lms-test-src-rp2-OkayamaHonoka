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

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	void test01() {//指定のURLに遷移する
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

		//入力項目のクリアと各項目の入力(ログインID)
		var passwordInput = webDriver.findElement(By.name("password"));
		passwordInput.clear();
		passwordInput.sendKeys("Studenta01");

		//ログインボタンの押下
		webDriver.findElement(By.xpath("//input[@value='ログイン']")).click();

		//ページ遷移を待機する
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

		//ページ遷移を待機する
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

}
