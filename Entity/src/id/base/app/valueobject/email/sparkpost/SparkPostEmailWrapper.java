package id.base.app.valueobject.email.sparkpost;

import id.base.app.SystemParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkPostEmailWrapper {
		
		private Map<String, Boolean> options;
		private Map<String, String> metadata;
		private Map<String, String> substitution_data;
		private List<Recipient> recipients;
		private Content content;
		
		public SparkPostEmailWrapper () {}
		
		public SparkPostEmailWrapper(String emailTo, String subject, String content, String html) {
			//default options
			this.options = SystemParameter.SparkPostConfiguration.DEFAULT_OPTIONS;
			//default metadata
			this.metadata = SystemParameter.SparkPostConfiguration.DEFAULT_METADATA;
			this.recipients = new ArrayList<Recipient>();
			//set recipient
			recipients.add(new Recipient(emailTo, new String[]{new String("registration")}));
			//set content
			this.content = new Content(subject, content, html);
			this.substitution_data = SystemParameter.SparkPostConfiguration.DEFAULT_SUBSTITUTION_DATA;
		}
		
		public Map<String, Boolean> getOptions() {
			return options;
		}
		public void setOptions(Map<String, Boolean> options) {
			this.options = options;
		}

		public Map<String, String> getMetadata() {
			return metadata;
		}
		public void setMetadata(Map<String, String> metadata) {
			this.metadata = metadata;
		}

		public Map<String, String> getSubstitution_data() {
			return substitution_data;
		}
		public void setSubstitution_data(Map<String, String> substitution_data) {
			this.substitution_data = substitution_data;
		}

		public List<Recipient> getRecipients() {
			return recipients;
		}
		public void setRecipients(List<Recipient> recipients) {
			this.recipients = recipients;
		}

		public Content getContent() {
			return content;
		}
		public void setContent(Content content) {
			this.content = content;
		}

		class Recipient {
			
			private Address address;
			private String[] tags;
			private Map<String, String> substitution_data;
			
			Recipient () {}
			
			Recipient (String email, String[] tags) {
				this.address = new Address(email);
				this.tags = tags;
				this.substitution_data = SystemParameter.SparkPostConfiguration.DEFAULT_RECIPIENT_SUBSTITUTION_DATA;
			}
			
			public Address getAddress() {
				return address;
			}
			public void setAddress(Address address) {
				this.address = address;
			}
			
			public String[] getTags() {
				return tags;
			}
			public void setTags(String[] tags) {
				this.tags = tags;
			}
			
			public Map<String, String> getSubstitution_data() {
				return substitution_data;
			}
			public void setSubstitution_data(Map<String, String> substitution_data) {
				this.substitution_data = substitution_data;
			}
			
			class Address {
				
				private String email;
				
				Address () {}
				
				Address (String email) {
					this.email = email;
				}
				
				public String getEmail() {
					return email;
				}
				public void setEmail(String email) {
					this.email = email;
				}
				
			}
			
		}
		
		class Content {
			
			private From from;
			private String subject;
			private String text;
			private String html;
			
			Content () {}
			
			Content (String subject, String text, String html) {
				this.from = new From(SystemParameter.SparkPostConfiguration.DEFAULT_FROM_NAME, SystemParameter.SparkPostConfiguration.DEFAULT_FROM_EMAIL);
				this.subject = subject;
				this.text = text;
				this.html = html;
			}
			
			public From getFrom() {
				return from;
			}
			public void setFrom(From from) {
				this.from = from;
			}

			public String getSubject() {
				return subject;
			}
			public void setSubject(String subject) {
				this.subject = subject;
			}

			public String getText() {
				return text;
			}
			public void setText(String text) {
				this.text = text;
			}

			public String getHtml() {
				return html;
			}
			public void setHtml(String html) {
				this.html = html;
			}

			class From {
			
				private String name;
				private String email;
				
				From () {}
				
				From (String name, String email) {
					this.name = name;
					this.email = email;
				}
				
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				
				public String getEmail() {
					return email;
				}
				public void setEmail(String email) {
					this.email = email;
				}
				
			}
		}
		
	}
