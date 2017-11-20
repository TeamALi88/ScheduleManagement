#coding:utf-8
from flask import Flask, request, send_from_directory, jsonify
from werkzeug import secure_filename
from flask_script import Manager 
import psycopg2
import json
import os

UPLOAD_FOLDER = '/home/yujun/haoji/pictures'
ALLOWED_EXTENSIONS = set(['png','jpg','jpeg','gif'])

def create_app():
  app = Flask(__name__)
  app.config['JSON_AS_ASCII'] = False
  app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
  return app

application = create_app()

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.',1)[1] in ALLOWED_EXTENSIONS

@application.route('/user/signup', methods=['POST'])
def sign_up():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser password=123 dbname=exampledb')                                           
    cursor = conn.cursor()
    data = request.get_data()
    data = data.decode("utf-8")
    dict = json.loads(data)
    values = (dict['username'],dict['phonenum'],dict['password'],dict['qq'],dict['weibo'])
    try:
        cursor.execute('insert into public.user(username,phonenum,password,qq,weibo) values(%s,%s,%s,%s,%s);',values)
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 200})
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

@application.route('/user/login', methods=['POST'])
def log_in():
    conn = psycopg2.connect("host=localhost port=5432 user=dbuser password=123 dbname=exampledb")                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user where phonenum = %s;',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('select* from public.user where phonenum = %s;',phonenum)
                results = cursor.fetchone()
                record = {}
                record['username'] = results[0]
                record['phonenum'] = results[1]
                record['weibo'] = results[4]
                record['qq'] = results[3]
                record['picture'] = results[5]

                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 200,"data":record})
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 402})    
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

@application.route('/user/picture/upload', methods=['POST'])
def upload_pic():
    if request.method == 'POST':
        file = request.files['file']
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename))
            filenames = filename.split('.')
            phonenum = filenames[0]
            conn = psycopg2.connect('host=localhost port=5432 user=dbuser password=123 dbname=exampledb')                                           
            cursor = conn.cursor()
            values =(filename,phonenum)
            try:
                cursor.execute('update public.user set picture = %s where phonenum = %s;',values)
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state':200})
            except:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 400})
        else:
            return jsonify({'state': 400})


@application.route('/user/picture/download', methods=['POST'])
def download_pic():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser password=123 dbname=exampledb')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user where phonenum = %s;',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('select picture from public.user where phonenum = %s;',phonenum)
                pic = cursor.fetchone()
                print(pic)
                return send_from_directory(UPLOAD_FOLDER,pic[0])
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 402})    
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

@application.route('/user/change/data', methods=['POST'])
def change_data():
    conn = psycopg2.connect("host=localhost port=5432 user=dbuser password=123 dbname=exampledb")                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    values = (dict['username'],dict['phonenum'],dict['password'],dict['qq'],dict['weibo'])
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user where phonenum = %s;',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('delete from public.user where phonenum = %s;',phonenum)
                cursor.execute('insert into public.user(username,phonenum,password,qq,weibo) values(%s,%s,%s,%s,%s);',values)
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state':200})
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 402})    
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

@application.route('/user/change/password', methods=['POST'])
def change_password():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser password=123 dbname=exampledb')                                             
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    values = (dict['newpassword'], dict['phonenum'],)
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user where phonenum = %s;',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['oldpassword']:
                cursor.execute('update public.user set password = %s where phonenum = %s;', values)
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state':200})
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 402})
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})


@application.route('/user/sync', methods=['POST'])
def sync_data():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser password=123 dbname=exampledb')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user where phonenum = %s',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                dats = dict['data']
                for dat in dats:
                    if dat['state'] == 0:
                        values = (dat['activitytime'],dat['activity'],dat['clock'],
                                  dat['activityclass'],dat['activitytitle'],dat['phonenum'])
                        cursor.execute('insert into public.schedule(activitytime,activity,clock,activityclass,activitytitle,phonenum) values(%s,%s,%s,%s,%s,%s)',values)
                        cursor.execute('')
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 402})    
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

if __name__ == '__main__':
    application.run()