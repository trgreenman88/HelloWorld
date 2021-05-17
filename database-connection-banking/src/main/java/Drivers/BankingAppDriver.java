package Drivers;

import controllers.BankingAppController;
import com.revature.util.*;

public class BankingAppDriver {

	public static void main(String[] args) {
		ConnectionUtility connect = new ConnectionUtility();
		BankingAppController.init();

	}

}
