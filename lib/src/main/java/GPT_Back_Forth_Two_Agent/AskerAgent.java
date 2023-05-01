package GPT_Back_Forth_Two_Agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;

public class AskerAgent {
	
	
	public String generateQuestionAbout(String theme) {
		List<ChatMessage> messages = new ArrayList<>();
		String sys_msg = "you are to follow your instructions exactly"
				+ " For example, if asked to add 2 and 5, you answer \"7\", you must not say any words except for the exact answer";
		
		String msg = "Please act as a user that asks a chatbot questions and ask a profound and emotional question about " + theme;
		
		ChatMessage m = new ChatMessage(ChatMessageRole.SYSTEM.value(), sys_msg);
		messages.add(m);
		m = new ChatMessage(ChatMessageRole.ASSISTANT.value(), msg);
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
