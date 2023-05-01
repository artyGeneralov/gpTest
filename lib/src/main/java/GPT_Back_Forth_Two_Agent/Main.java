package GPT_Back_Forth_Two_Agent;

import com.theokanning.openai.service.OpenAiService;

public class Main {
	final public static String token = System.getenv("OAI_K");
	public static OpenAiService service = new OpenAiService(token);
	
	
	public static void main(String[] args) {
		/*AskerAgent asker = new AskerAgent();
		AnswererAgent answerer = new AnswererAgent();
		String q = asker.generateQuestionAbout("lab mice");
		System.out.printf("question:\n%s, \nanswer:\n%s", q, answerer.sarcasticAnswer(q));*/
		
		CalcBot calcBot = new CalcBot();
		String c = "Calculate the avarage of 2 4 6 8";
		System.out.println(calcBot.calc(c));
		
	}
}
