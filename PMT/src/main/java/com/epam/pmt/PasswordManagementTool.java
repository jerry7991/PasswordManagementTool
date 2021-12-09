package com.epam.pmt;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.epam.factory.Factories;
import com.epam.factory.Factory;
import com.epam.globaldata.Operations;
import com.epam.model.Response;
import com.epam.singleton.Loggers;
import com.epam.singleton.Reader;

@Configuration
@ComponentScan(basePackages = "com.epam.*")
public class PasswordManagementTool {
	@Autowired
	private Loggers LOGGER;

	public static void main(String[] args) {
		PasswordManagementTool pmt = new PasswordManagementTool();
		pmt.doProcess();
	}

	public void doProcess() {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(PasswordManagementTool.class);

		LOGGER = Loggers.getLogger();

		Factories factories = context.getBean(Factories.class);

		LOGGER.printInfo(PasswordManagementTool.class, printTemplate());
		BufferedReader reader = Reader.getReader();
		String choice = "notExit";

		while (!choice.equalsIgnoreCase("exit")) {
			LOGGER.printInfo(PasswordManagementTool.class, "Enter Choice ::");
			try {
				choice = reader.readLine();

				if (choice.equalsIgnoreCase("exit")) {
					break;
				}

				Response response = factories.getFactory(choice);

				if (!response.getStatus()) {
					LOGGER.printError(PasswordManagementTool.class, (String) response.getMsg());
					continue;
				}

				Factory factory = (Factory) response.getMsg();
				factory.execute();

			} catch (IOException ex) {
				LOGGER.printError(PasswordManagementTool.class, ex.getMessage());
			}
		}
		factories.close();
		Reader.closeReader();
	}

	public static String printTemplate() {
		StringBuilder out = new StringBuilder();
		out.append("Type bellow choice for performing action, i.e. \n action -> input required\n");

		out.append("Add new user -------> " + Operations.addUser + "\n");
		out.append("For terminate the process -------> " + Operations.exit + "\n");

		out.append("For creating new account -------> " + Operations.addAccount + "\n");

		out.append("delete account -------> " + Operations.deleteAccount + "\n");

		out.append("For view all the details with group by their name -------> view group by\n");

		out.append("For update account -------> update account\n");

		out.append("For update group name -------> update group name\n");

		out.append("For sepecific delete group -------> delete group\n");
		return out.toString();

	}
}
