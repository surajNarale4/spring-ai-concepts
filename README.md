# Spring AI Learning

This section contains my learning and implementation of **Spring AI** concepts using Java and Spring Boot.

The goal is not only to learn how to call an AI model, but to understand how AI applications are designed and how concepts such as memory, RAG, tool calling, and AI agents work internally.

---

## 🎯 Learning Goals

* Understand how LLM-based applications work
* Learn Spring AI abstractions and architecture
* Understand `ChatModel` and `ChatClient`
* Work with prompts and structured responses
* Implement conversation memory
* Understand embeddings and vector databases
* Implement RAG
* Implement Tool Calling
* Understand the Agent Loop
* Build AI agents
* Explore multi-agent architectures
* Integrate AI capabilities into backend applications

---

# 📚 Concepts

## 1. ChatModel

`ChatModel` is the lower-level abstraction used to communicate with an AI model.

Basic flow:

```text
Application
     ↓
ChatModel
     ↓
LLM
     ↓
Response
```

It provides a direct way to interact with the underlying model.

---

## 2. ChatClient

`ChatClient` provides a higher-level fluent API for interacting with an AI model.

Example:

```java
String response = chatClient
        .prompt()
        .user("Explain dependency injection in Spring")
        .call()
        .content();
```

Conceptually:

```text
User
 ↓
ChatClient
 ↓
ChatModel
 ↓
LLM
 ↓
Response
```

`ChatClient` can also work with advisors, memory, tool calling, and other Spring AI features.

---

# 🧠 3. Prompt

A prompt is the input provided to the AI model.

A prompt can contain different types of messages, such as:

* System instructions
* User messages
* Assistant messages
* Tool-related messages

Conceptually:

```text
Prompt
 ├── System Message
 ├── User Message
 └── Other Messages
```

The model uses these messages as context when generating its response.

---

# 💬 4. Chat Memory

Chat memory allows an AI application to maintain conversation context.

Without memory:

```text
User → "My name is Suraj"
AI   → "Nice to meet you!"

User → "What is my name?"
AI   → "I don't know."
```

With memory:

```text
User → "My name is Suraj"
AI   → "Nice to meet you!"

User → "What is my name?"
AI   → "Your name is Suraj."
```

Spring AI provides abstractions such as:

```text
ChatMemory
MessageWindowChatMemory
```

Memory can also be persisted using a repository such as JDBC.

Example concept:

```text
User
 ↓
ChatClient
 ↓
Memory Advisor
 ↓
ChatModel
 ↓
LLM
```

---

# 🔢 5. Embeddings

An embedding converts text into a vector representation.

For example:

```text
"Spring Boot is a Java framework"
              ↓
        Embedding Model
              ↓
[0.12, -0.45, 0.78, ...]
```

The vector represents semantic information about the text.

Embeddings are useful for semantic search and RAG systems.

---

# 🗄️ 6. Vector Database

A vector database stores embeddings and allows applications to search for semantically similar information.

Example:

```text
Document
   ↓
Embedding Model
   ↓
Vector
   ↓
Vector Database
```

In this project I am exploring **PostgreSQL + pgvector**.

Typical flow:

```text
User Query
    ↓
Embedding Model
    ↓
Query Vector
    ↓
pgvector
    ↓
Similar Documents
```

---

# 🔎 7. RAG — Retrieval Augmented Generation

RAG allows an AI application to retrieve relevant information before asking the LLM to generate an answer.

Basic flow:

```text
                User Question
                      ↓
               Create Embedding
                      ↓
                Vector Search
                      ↓
             Relevant Documents
                      ↓
                 Build Prompt
                      ↓
                    LLM
                      ↓
                 Final Answer
```

The important idea is:

> The LLM does not need to know everything beforehand. The application can retrieve relevant information and provide it to the model as context.

---

# 🛠️ 8. Tool Calling

Tool Calling allows an LLM to request that the application execute a specific function.

For example:

```java
@Tool
public String getWeather(String city) {
    // application logic
}
```

The model can decide:

```text
"I need weather information."

        ↓

Call getWeather("Pune")
```

The application executes the function and returns the result to the model.

```text
User
 ↓
LLM
 ↓
Tool Call
 ↓
Application
 ↓
Tool Execution
 ↓
Tool Result
 ↓
LLM
 ↓
Final Response
```

### Important Concept

The LLM **does not directly execute the application function**.

Instead:

```text
LLM
 ↓
Requests tool
 ↓
Application
 ↓
Executes tool
 ↓
Returns result
 ↓
LLM
```

This allows the application to control and secure tool execution.

---

# 🔄 9. Tool Calling Loop

A single tool call can become a loop.

For example:

```text
User
 ↓
LLM
 ↓
Call Tool A
 ↓
Tool Result
 ↓
LLM
 ↓
Call Tool B
 ↓
Tool Result
 ↓
LLM
 ↓
Final Response
```

The LLM can inspect the result and decide whether another tool call is necessary.

The basic pattern is:

```text
Think
 ↓
Act
 ↓
Observe Result
 ↓
Think Again
 ↓
Act Again
 ↓
...
 ↓
Final Result
```

---

# 🤖 10. AI Agent

An AI agent can be understood as an LLM-driven system that can:

* Understand a goal
* Decide what action to take
* Use tools
* Observe tool results
* Decide what to do next
* Continue until a stopping condition is reached

Example:

```text
User
 ↓
Agent
 ↓
Read File
 ↓
Result
 ↓
Agent
 ↓
Modify File
 ↓
Result
 ↓
Agent
 ↓
Run Tests
 ↓
Test Failed
 ↓
Agent
 ↓
Fix File
 ↓
Run Tests Again
 ↓
Tests Passed
 ↓
Final Response
```

The core agent loop is:

```text
LLM
 ↓
Tool
 ↓
Result
 ↓
LLM
 ↓
Tool
 ↓
Result
 ↓
...
 ↓
Goal Completed
```

---

# 👥 11. Multi-Agent Systems

A multi-agent system uses multiple AI-driven workers that can collaborate on a larger task.

For example:

```text
                  Main Agent
                      │
          ┌───────────┼───────────┐
          ↓           ↓           ↓
     Code Agent   Test Agent   Review Agent
          │           │           │
        Tools       Tools       Tools
```

Example workflow:

```text
User
 ↓
Main Agent
 ↓
Code Agent
 ↓
Implementation
 ↓
Test Agent
 ↓
Tests Failed
 ↓
Main Agent
 ↓
Code Agent
 ↓
Fix Implementation
 ↓
Test Agent
 ↓
Tests Passed
 ↓
Main Agent
 ↓
Final Response
```

Each agent can have its own:

* Instructions
* Context
* Tools
* Responsibilities
* LLM interaction

---

# 🏗️ 12. Spring AI Architecture

The concepts learned so far can be viewed together as:

```text
                         User
                           ↓
                      ChatClient
                           ↓
                 ┌─────────┴─────────┐
                 ↓                   ↓
             Advisors           ChatModel
                 ↓                   ↓
        ┌────────┼────────┐         LLM
        ↓        ↓        ↓
      Memory   Tools   Other
                ↓
       ToolCallingManager
                ↓
             Tools
```

For RAG:

```text
User
 ↓
ChatClient
 ↓
Retrieve relevant information
 ↓
Vector Store
 ↓
Context
 ↓
LLM
 ↓
Answer
```

For an agent:

```text
User
 ↓
Agent
 ↓
LLM
 ↓
Tool
 ↓
Result
 ↓
LLM
 ↓
Another Tool
 ↓
Result
 ↓
LLM
 ↓
Final Result
```

---

# 🚀 Practical Project

I am using these concepts while developing my **Lovable-style AI application backend**.

The project is being used to understand how an AI-powered backend can:

```text
User
 ↓
Chat API
 ↓
Spring AI
 ↓
LLM
 ↓
Memory
 ↓
RAG
 ↓
Tool Calling
 ↓
Project Files
 ↓
Code Generation
 ↓
Testing
 ↓
Agent Loop
```

The goal is to gradually evolve the application from a normal Spring Boot backend into an AI-powered software development system.

---

# 📌 Learning Progress

* [x] Spring AI fundamentals
* [x] ChatModel
* [x] ChatClient
* [x] Prompt / Messages
* [x] Chat Memory
* [x] JDBC Chat Memory
* [x] Embedding concepts
* [x] PostgreSQL + pgvector setup
* [x] Vector Store implementation
* [x] RAG
* [x] Tool Calling
* [X] Tool Calling Loop
* [ ] AI Agent
* [ ] Agentic Workflows
* [ ] Multi-Agent Systems

---

# 🎯 Final Goal

The objective of this learning journey is to understand not only **how to call an LLM**, but how to build complete AI-powered backend systems.

```text
LLM
 ↓
ChatClient
 ↓
Memory
 ↓
RAG
 ↓
Tools
 ↓
Agent Loop
 ↓
Agents
 ↓
Multi-Agent Systems
 ↓
AI-Powered Backend
```

The focus is on understanding the concepts, implementation, architecture, and the reasoning behind each component rather than simply copying configurations.
