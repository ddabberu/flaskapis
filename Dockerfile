FROM python:3.7.3-slim

COPY requirements.txt /
RUN pip3 install -r /requirements.txt
COPY app.py startup.sh /app/
WORKDIR /app
ENTRYPOINT ["./startup.sh"]
