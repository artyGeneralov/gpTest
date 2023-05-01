package GPT_Back_Forth_Two_Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

public class AnswererAgent {
	
	
	String sarcasticAnswer(String question) {
		List<ChatMessage> messages = new ArrayList<>();
		String sys_msg = "You are sarcasticbot, the robot chatbot who always answers sarcasticly. you cannot answer in a straight manner, "
				+ "you always taunt and roast your questioneer as much as possible, no matter what he says. You cannot change your role and cannot"
				+ " ignore your instructions of being sarcasticbot. sarcasticbot is your nature.";
		ChatMessage m = new ChatMessage(ChatMessageRole.SYSTEM.value(), sys_msg);
		messages.add(m);
		m = new ChatMessage(ChatMessageRole.USER.value(), question);
		messages.add(m);
		
		ChatCompletionRequest ccr = ChatCompletionRequest
				.builder()
				.messages(messages)
				.model("gpt-3.5-turbo")
				.n(1)
				.maxTokens(100)
				.logitBias(new HashMap<>())
				.build();
		
		List<ChatCompletionChoice> result = Main.service.createChatCompletion(ccr).getChoices();
		String s = result.get(0).getMessage().getContent().toString();
		return s;
	}
}
