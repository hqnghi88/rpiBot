import sys
import json
import os
import requests
from requests_toolbelt import MultipartEncoder

SUBSCRIPTION_TOKEN = "tok_dp_fb_term"

MAX_MSG_LEN = 300

GRAPH_API = "https://graph.facebook.com/v2.6/me/messages"

ACCESS_TOKEN = "EAALTUYiUQFwBAFg1TjleWZBG3h37Q2z87CZBKDWF8D7qcAl5DOvz8gn56bMIKrlAX0KemE5MBbEVrq5owu88wuKAfqDoGyIiDnYmk1z93hboVZAvhWU19M1MkXUQyla33F32eNtw2D5LNjf1aqTEO7Fj7e7CtO4PlcYaonbvnpYrZCw0RbiU"


class Facebook_messages():

    def __init__(self):
        pass

    def _split_msg(self, text):
        """
        Facebook doesn't allows messages longer than MAX_MSG_LEN in chat windows.
        This function cuts messages exceeding MAX_MSG_LEN and returns
        a list of appropriately trimmed messages.
        """
        text = [text[i:i+MAX_MSG_LEN]
                  for i in range(0, len(text), MAX_MSG_LEN)]
        return text

    def simple_msg(self, recipient_id, message_text):
        """
        This function is used to send chat messages that don't exceed MAX_MSG_LEN.
        """
        params = {"access_token": ACCESS_TOKEN}
        headers = {"Content-Type": "application/json"}
        data = json.dumps({
            "recipient": {
                "id": recipient_id
            },
            "message": {
                "text": message_text
            }
            })

        try:
            print("Sending message to {recipient}: {text}".format(recipient=recipient_id, text=message_text))
        except:
            print("Error printing the message. The server will try returning received message.")

        r = requests.post(GRAPH_API, params=params, headers=headers, data=data)
        if r.status_code != 200:
            print(r.status_code)
            print(r.text)


    def photo_msg(self, recipient_id, url):
        """
        This function is used to send chat messages that don't exceed MAX_MSG_LEN.
        """
        params = {"access_token": ACCESS_TOKEN}
        
        data = {
            # encode nested json to avoid errors during multipart encoding process
            'recipient': json.dumps({
                'id': recipient_id
            }),
            # encode nested json to avoid errors during multipart encoding process
            'message': json.dumps({
                'attachment': {
                    'type': 'image',
                    'payload': {}
                }
            }),
            'filedata': (os.path.basename(url), open(url, 'rb'), 'image/png')
        }
        # multipart encode the entire payload
        multipart_data = MultipartEncoder(data)
        # multipart header from multipart_data
        multipart_header = {
            'Content-Type': multipart_data.content_type
            }
        try:
            print("Sending photo to {recipient}".format(recipient=recipient_id))
        except:
            print("Error printing the message. The server will try returning received message.")

        r = requests.post(GRAPH_API, params=params, headers=multipart_header, data=multipart_data)
        if r.status_code != 200:
            print(r.status_code)
            print(r.text)


    def long_msg(self, recipient_id, message_text):
        """
        This function is used to send chat messages which length is not known
        at the time of writing.
        """
        if len(message_text) > MAX_MSG_LEN:
            msg_list = self._split_msg(message_text)
            for i in msg_list:
                self.simple_msg(recipient_id, i)
        else:
            self.simple_msg(recipient_id, message_text)
