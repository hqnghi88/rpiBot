import facebook
import sys


# Dependencies
fb = facebook.Facebook_messages()
sender_id = "1839231356117396"

fb.simple_msg(sender_id, str(sys.argv[1]))
                       
