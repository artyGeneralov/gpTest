package GPT_Back_Forth_Two_Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

public class CalcBot {

	List<ChatMessage> messages;
	String sys_msg = "for the following calculation request, you must not perform any actual calculation."
			+ " instead, give me a list of calculations that needs to be done in the next step."
			+ " assume the answers to those calculations would be given later. "
			+ " do not write any words, just give out a list of needed calculations."
			+ " for example: if the question is \" what is the sum of the first 4 integers? \""
			+ " you should answer:"
			+ " 1.) add(1,2) 2.) add(3,4)"
			+ " do not perform the calculations themselves."
			+ " The math library that you have access to is such:"
			+ " add(a,b) - to add two integers"
			+ " sub(a,b) - to subtract b from a"
			+ " mul(a,b) - to multiply a and b"
			+ " div(a,b) - to divide a by b"
			+ " your answer should be in the format of the above library"
			+ " if a step requires the result of previous steps you should write instead of each parameter"
			+ " the relevant calculation, for example add(add(a,b), c). "
			+ " the task: ";
	
	public CalcBot() {
		messages = new ArrayList<>();
		ChatMessage m = new ChatMessage(ChatMessageRole.SYSTEM.value(), sys_msg);
		messages.add(m);
	}
	
	String calc(String prmpt) {
		String res = "";
		ChatMessage m = new ChatMessage(ChatMessageRole.USER.value(), prmpt);
		messages.add(m);
		ChatCompletionRequest ccr = ChatCompletionRequest
				.builder()
				.model("gpt-3.5-turbo")
				.messages(messages)
				.n(1)
				.maxTokens(100)
				.logitBias(new HashMap<>())
				.build();
		
		List<ChatCompletionChoice> result = Main.service.createChatCompletion(ccr).getChoices();
		List<ChatMessage> temp = new ArrayList<>();
		for(ChatMessage c : messages)
			temp.add(c);
		temp.add(result.get(0).getMessage());
		ChatMessage ans = reflect(temp);
		
		res = ans.getContent().toString();
		
		return res;
	}
	
	ChatMessage reflect(List<ChatMessage> context) {
		String ref_s = "Please review the last answer and refine the answer in such a way that complies with all of the instructions,"
				+ " especially comply with the main instruction regarding the formatting and the performing of calculations"
				+ " also make sure that the steps are correct";
		ChatMessage m = new ChatMessage(ChatMessageRole.ASSISTANT.value(), ref_s);
		List<ChatMessage> ctx = new ArrayList<>();
		for(ChatMessage c : context)
			ctx.add(c);
		ctx.add(m);
		
		ChatCompletionRequest ccr = ChatCompletionRequest
				.builder()
				.model("gpt-3.5-turbo")
				.messages(messages)
				.n(1)
				.maxTokens(100)
				.logitBias(new HashMap<>())
				.build();
		
		List<ChatCompletionChoice> result = Main.service.createChatCompletion(ccr).getChoices();
		
		
		return result.get(0).getMessage();
	}
	
	
	private int add(int a, int b) {
		return a+b;
	}
	
	private int sub(int a, int b) {
		return a-b;
	}
	
	private int mul(int a, int b) {
		return a*b;
	}
	
	private int div(int a, int b) {
		return Math.floorDiv(a, b);
	}
}
