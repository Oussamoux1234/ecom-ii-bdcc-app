# Spring AI MCP + Telegram Bot - Quick Reference

## Environment Variables Setup

Before running, set these environment variables:

```bash
# OpenAI API Key
OPENAI_API_KEY=sk-your-actual-openai-api-key

# Telegram Bot Credentials (from @BotFather)
TELEGRAM_BOT_TOKEN=your-telegram-bot-token
TELEGRAM_BOT_USERNAME=your_bot_username
```

## Running Order

1. **MCP Server** (port 8989) - MUST run first
2. **Agent Bot** (port 8087) - Connects to MCP server

## Test Commands for Telegram

- "Who is Hassan?"
- "What is his salary?"
- "List all employees"
- "Who has the highest salary?"
