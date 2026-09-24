package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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

}
