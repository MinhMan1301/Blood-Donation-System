#!/bin/bash

# Blood Donation System - Run Script
# This script helps you run the application with Java 17

echo "🩸 Blood Donation Management System"
echo "===================================="
echo ""

# Check if Java 17 is available
if command -v /usr/libexec/java_home &> /dev/null; then
    # macOS
    JAVA_17_HOME=$(/usr/libexec/java_home -v 17 2>/dev/null)
    if [ -z "$JAVA_17_HOME" ]; then
        echo "❌ Java 17 not found!"
        echo "Please install Java 17 first."
        echo ""
        echo "On macOS with Homebrew:"
        echo "  brew install openjdk@17"
        exit 1
    fi
    export JAVA_HOME=$JAVA_17_HOME
else
    # Linux/Other
    if ! command -v java &> /dev/null; then
        echo "❌ Java not found!"
        echo "Please install Java 17 first."
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" != "17" ]; then
        echo "⚠️  Warning: Java version is $JAVA_VERSION, but Java 17 is recommended."
        echo "Continuing anyway..."
    fi
fi

echo "✅ Using Java: $JAVA_HOME"
echo ""

# Check if MySQL is running
if command -v mysql &> /dev/null; then
    if mysql -u root -e "USE blood_donation;" 2>/dev/null; then
        echo "✅ Database 'blood_donation' found"
    else
        echo "⚠️  Warning: Database 'blood_donation' not found or cannot connect"
        echo "Please import the database first:"
        echo "  mysql -u root -p < blood_donation1.sql"
        echo "  mysql -u root -p < data.sql"
        echo ""
        read -p "Continue anyway? (y/n) " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    fi
else
    echo "⚠️  MySQL command not found. Make sure MySQL is installed and running."
fi

echo ""
echo "🚀 Starting application..."
echo "===================================="
echo ""

# Kill any process using port 8081
if lsof -ti:8081 &> /dev/null; then
    echo "⚠️  Port 8081 is in use. Killing existing process..."
    lsof -ti:8081 | xargs kill -9 2>/dev/null
    sleep 2
fi

# Run the application
mvn spring-boot:run

# If Maven fails, try building first
if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Failed to start. Trying to build first..."
    mvn clean install -DskipTests
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Build successful. Starting application..."
        mvn spring-boot:run
    fi
fi
