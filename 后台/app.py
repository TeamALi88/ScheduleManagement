'''
俞鋆完成于12月25日

注释掉的代码为软件还未使用的
'''
#coding:utf-8
from flask import Flask, request, send_from_directory, jsonify
from werkzeug import secure_filename
from flask_script import Manager 
import psycopg2
import json
import os

'''
UPLOAD_FOLDER = '/home/yujun/haoji/pictures'                                   #存储头像图片的位置
ALLOWED_EXTENSIONS = set(['png','jpg','jpeg','gif'])                           #允许头像图片的格式
'''
def create_app():                                                              #创建app
  app = Flask(__name__)
  app.config['JSON_AS_ASCII'] = False                                          #将默认的数据编码ASCII改为false，可解决交换数据中文乱码的问题。
  app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
  return app

application = create_app()

'''
def allowed_file(filename):                                                    #将文件名作为文件存储名
    return '.' in filename and filename.rsplit('.',1)[1] in ALLOWED_EXTENSIONS
'''

@application.route('/user/signup', methods=['POST'])                           #用户注册接口
def sign_up():
    conn = psycopg2.connect('''host=localhost port=5432 user=dbuser 
                                   password=123 dbname=exampledb''')           #登录本地的Postgres数据库                                           
    cursor = conn.cursor()                                                     
    data = request.get_data()
    data = data.decode("utf-8")
    dict = json.loads(data)
    values = (dict['username'],dict['phonenum'],
              dict['password'],dict['qq'],dict['weibo'])
    try:
        cursor.execute('''insert into public.user(username,phonenum,
                       password,qq,weibo) values(%s,%s,%s,%s,%s);''',values)   #查询数据库
        cursor.close()                                                      
        conn.commit()
        conn.close()                                                           #关闭数据库
        return jsonify({'state': 200})                                         #返回成功状态码
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})                                         #返回失败状态码

@application.route('/user/login', methods=['POST'])                            #用户登录接口
def log_in():
    conn = psycopg2.connect('''host=localhost port=5432 user=dbuser 
                            password=123 dbname=exampledb''')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('''select password from public.user 
                       where phonenum = %s;''',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('''select* from public.user 
                               where phonenum = %s;''',phonenum)
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

'''

@application.route('/user/picture/upload', methods=['POST'])                   #用户上传头像的接口
def upload_pic():
    if request.method == 'POST':
        file = request.files['file']
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename))
            filenames = filename.split('.')
            phonenum = filenames[0]
            conn = psycopg2.connect('host=localhost port=5432 
                                     user=dbuser password=123 dbname=exampledb')                                           
            cursor = conn.cursor()
            values =(filename,phonenum)
            try:
                cursor.execute('update public.user set picture = %s 
                                where phonenum = %s;',values)
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


@application.route('/user/picture/download', methods=['POST'])                 #用户下载头像的接口
def download_pic():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser 
                             password=123 dbname=exampledb')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user 
                        where phonenum = %s;',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('select picture from public.user 
                                where phonenum = %s;',phonenum)
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

'''

@application.route('/user/change/data', methods=['POST'])                      #用户更改个人信息的接口
def change_data():
    conn = psycopg2.connect('''host=localhost port=5432 user=dbuser 
                             password=123 dbname=exampledb''')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    values = (dict['username'],dict['phonenum'],
              dict['password'],dict['qq'],dict['weibo'])
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('''select password from public.user 
                        where phonenum = %s;''',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['password']:
                cursor.execute('''delete from public.user 
                                where phonenum = %s;''',phonenum)
                cursor.execute('''insert into public.user(username,phonenum,
                                password,qq,weibo) values(%s,%s,%s,%s,%s);''',values)
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

@application.route('/user/change/password', methods=['POST'])                  #用户修改密码的接口
def change_password():
    conn = psycopg2.connect('''host=localhost port=5432 user=dbuser 
                             password=123 dbname=exampledb''')                                             
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    values = (dict['newpassword'], dict['phonenum'],)
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('''select password from public.user 
                        where phonenum = %s;''',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            pswd = result[0]
            if pswd == dict['oldpassword']:
                cursor.execute('''update public.user set password = %s 
                                where phonenum = %s;''', values)
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

@application.route('/user/reset/password', methods=['POST'])                   #用户重置密码的接口
def reset_password():
    conn = psycopg2.connect('''host=localhost port=5432 user=dbuser 
                             password=123 dbname=exampledb''')                                             
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    values = (dict['newpassword'], dict['phonenum'],)
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('''select username from public.user 
                        where phonenum = %s;''',phonenum)
        result = cursor.fetchone()
        if result == None:
            cursor.close()
            conn.commit()
            conn.close()
            return jsonify({'state':401})
        else:
            usn = result[0]
            if usn == dict['username']:
                cursor.execute('''update public.user set password = %s 
                                where phonenum = %s;''', values)
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state':200})
            else:
                cursor.close()
                conn.commit()
                conn.close()
                return jsonify({'state': 403})
    except:
        cursor.close()
        conn.commit()
        conn.close()
        return jsonify({'state': 400})

'''
@application.route('/user/sync', methods=['POST'])                             #用户本地数据库和服务器数据库同步的接口
def sync_data():
    conn = psycopg2.connect('host=localhost port=5432 user=dbuser 
                             password=123 dbname=exampledb')                                           
    cursor = conn.cursor()
    data = request.get_data()
    dict = json.loads(data.decode("utf-8"))
    phonenum = (dict['phonenum'],)
    try:
        cursor.execute('select password from public.user 
                        where phonenum = %s',phonenum)
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
                        cursor.execute('insert into public.schedule(activitytime,activity,clock,
                                        activityclass,activitytitle,phonenum) values(%s,%s,%s,%s,%s,%s)',values)
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
'''

if __name__ == '__main__':
    application.run()