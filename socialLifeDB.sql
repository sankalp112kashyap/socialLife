CREATE TABLE users (
  user_id INT AUTO_INCREMENT,
  username VARCHAR(255),
  password VARCHAR(255),

  first_name VARCHAR(255),
  last_name VARCHAR(255),
  sex VARCHAR(10),
  email VARCHAR(255),
  status VARCHAR(255) DEFAULT 'Hello There',
  dob TIMESTAMP DEFAULT NOW(),

  created_at TIMESTAMP DEFAULT NOW(),
  profile_pic_path VARCHAR(255) DEFAULT 'assets/img/default_pic.png',


  PRIMARY KEY (user_id)
);


-- users
CREATE TABLE pages (
    page_id INT AUTO_INCREMENT ,
    user_id INT,

    page_name VARCHAR(255),
    page_description VARCHAR(255),
    page_pic_path VARCHAR(255) DEFAULT 'default_pic.jpg',

    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY(user_id) REFERENCES users(user_id),

    PRIMARY KEY (page_id)
);

CREATE TABLE content_show_page (
    content_id INT,
    show_to_page_id INT,

    FOREIGN KEY (content_id) REFERENCES content_posts(content_id),
    FOREIGN KEY (show_to_page_id) REFERENCES pages(page_id),
    PRIMARY KEY (content_id, show_to_page_id)

)


CREATE TABLE content_posts (
  content_id INT PRIMARY KEY AUTO_INCREMENT,
  belongs_to_page_id INT,
  post_text VARCHAR (255) NOT NULL,
  like_count INT DEFAULT 0,
  view_count INT DEFAULT 0,
  image_path VARCHAR (255) DEFAULT 'default_post_pic.png',
  created_at TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (belongs_to_page_id) REFERENCES pages(page_id)
)

-- whenever budget <= 0, then
  CREATE TABLE ad_budget (

    content_id INT PRIMARY KEY,
    budget NUMERIC,
    FOREIGN KEY (content_id) REFERENCES content_posts(content_id)

  );


CREATE TABLE page_monetization (
  page_id INT PRIMARY KEY,
  money_received NUMERIC,
  FOREIGN KEY (page_id) REFERENCES pages(page_id)

);





















CREATE TABLE advertisers (
  advertiser_id INT AUTO_INCREMENT,
  user_id INT,
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (advertiser_id, user_id),
  FOREIGN KEY (user_id) REFERENCES user_table(user_id)
);




CREATE TABLE posts (
  post_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  post_text VARCHAR (255) NOT NULL,
  click_count INT DEFAULT 0,
  view_count INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT NOW(),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
{"data":{"solutions":[{"language_token":"java","language_name":"Java (SE 1.8)","solution_code":"import java.io.IOException;\r\nimport java.util.*;\r\n\r\npublic class Main {\r\n\tstatic FasterScanner in = new FasterScanner(); \r\n\t\r\n\tstatic int MAX = 300001;\r\n\tstatic long [][] Table = new long [MAX][20];\r\n\tstatic long [] A = new long [MAX];\r\n\tstatic long gcd(long a, long b)\r\n\t{\r\n\t    if(b==0)\r\n\t        return a;\r\n\t    else\r\n\t        return gcd(b, a%b);\r\n\t}\r\n\tstatic void preprocess(int n)\r\n\t{\r\n\t\tint i, j;\r\n\t    //initialize Table\r\n\t    for(i=0; i\u003cn; i++)\r\n\t        Table[i][0] = A[i];\r\n\t    //compute values from smaller to bigger intervals\r\n\t    for (j = 1; 1 \u003c\u003c j \u003c= n; j++)\r\n\t        for (i = 0; i + (1 \u003c\u003c j) - 1 \u003c n; i++)\r\n\t        {\r\n\t            // j tells how much further to move from i\r\n\t            if(Table[i][j - 1]\u003eTable[i + (1 \u003c\u003c (j - 1))][j - 1])\r\n\t                Table[i][j] = gcd(Table[i][j - 1],Table[i + (1 \u003c\u003c (j - 1))][j - 1]);\r\n\t            else\r\n\t                Table[i][j] = gcd(Table[i + (1 \u003c\u003c (j - 1))][j - 1], Table[i][j - 1]);\r\n\t        }\r\n\t}\r\n\tstatic long query(int l, int r, int n)\r\n\t{\r\n\t\tint k=20;\r\n\t\tlong ans=0;\r\n\t    int i;\r\n\t    for(i=k; i\u003e=0; i--)\r\n\t    {\r\n\t        if(l+(1\u003c\u003ci)-1\u003c=r)\r\n\t        {\r\n\t            if(ans\u003eTable[l][i])\r\n\t                ans=gcd(ans, Table[l][i]);\r\n\t            else\r\n\t                ans=gcd(Table[l][i], ans);\r\n\t            l+=1\u003c\u003ci;//increasing l\r\n\t        }\r\n\t    }\r\n\t    return ans;\r\n\r\n\t}\r\n\tpublic static void main(String[] args)throws Exception {\r\n//\t\tScanner s = new Scanner(System.in);\r\n\t\tint n, q, l, r, i;\r\n\t\tlong ans;\r\n\t    n=in.nextInt();\r\n\t    for(i=0; i\u003cn; i++)A[i]=in.nextLong();\r\n\t    preprocess(n);\r\n\t    q=in.nextInt();\r\n\t    while(q--\u003e0)\r\n\t    {\r\n\t        l=in.nextInt(); r=in.nextInt();\r\n\t        l--;r--;\r\n\t        ans=query(l, r, n);\r\n\t        System.out.println(ans);\r\n\t    }\r\n\t}\r\n\r\n\tstatic class FasterScanner {\r\n\t\tprivate byte[] buf = new byte[1024];\r\n\t\tprivate int curChar;\r\n\t\tprivate int snumChars;\r\n\r\n\t\tpublic int read() {\r\n\t\t\tif (snumChars == -1)\r\n\t\t\t\tthrow new InputMismatchException();\r\n\t\t\tif (curChar \u003e= snumChars) {\r\n\t\t\t\tcurChar = 0;\r\n\t\t\t\ttry {\r\n\t\t\t\t\tsnumChars = System.in.read(buf);\r\n\t\t\t\t} catch (IOException e) {\r\n\t\t\t\t\tthrow new InputMismatchException();\r\n\t\t\t\t}\r\n\t\t\t\tif (snumChars \u003c= 0)\r\n\t\t\t\t\treturn -1;\r\n\t\t\t}\r\n\t\t\treturn buf[curChar++];\r\n\t\t}\r\n\r\n\t\tpublic String nextLine() {\r\n\t\t\tint c = read();\r\n\t\t\twhile (isSpaceChar(c))\r\n\t\t\t\tc = read();\r\n\t\t\tStringBuilder res = new StringBuilder();\r\n\t\t\tdo {\r\n\t\t\t\tres.appendCodePoint(c);\r\n\t\t\t\tc = read();\r\n\t\t\t} while (!isEndOfLine(c));\r\n\t\t\treturn res.toString();\r\n\t\t}\r\n\r\n\t\tpublic String nextString() {\r\n\t\t\tint c = read();\r\n\t\t\twhile (isSpaceChar(c))\r\n\t\t\t\tc = read();\r\n\t\t\tStringBuilder res = new StringBuilder();\r\n\t\t\tdo {\r\n\t\t\t\tres.appendCodePoint(c);\r\n\t\t\t\tc = read();\r\n\t\t\t} while (!isSpaceChar(c));\r\n\t\t\treturn res.toString();\r\n\t\t}\r\n\r\n\t\tpublic long nextLong() {\r\n\t\t\tint c = read();\r\n\t\t\twhile (isSpaceChar(c))\r\n\t\t\t\tc = read();\r\n\t\t\tint sgn = 1;\r\n\t\t\tif (c == '-') {\r\n\t\t\t\tsgn = -1;\r\n\t\t\t\tc = read();\r\n\t\t\t}\r\n\t\t\tlong res = 0;\r\n\t\t\tdo {\r\n\t\t\t\tif (c \u003c '0' || c \u003e '9')\r\n\t\t\t\t\tthrow new InputMismatchException();\r\n\t\t\t\tres *= 10;\r\n\t\t\t\tres += c - '0';\r\n\t\t\t\tc = read();\r\n\t\t\t} while (!isSpaceChar(c));\r\n\t\t\treturn res * sgn;\r\n\t\t}\r\n\r\n\t\tpublic int nextInt() {\r\n\t\t\tint c = read();\r\n\t\t\twhile (isSpaceChar(c))\r\n\t\t\t\tc = read();\r\n\t\t\tint sgn = 1;\r\n\t\t\tif (c == '-') {\r\n\t\t\t\tsgn = -1;\r\n\t\t\t\tc = read();\r\n\t\t\t}\r\n\t\t\tint res = 0;\r\n\t\t\tdo {\r\n\t\t\t\tif (c \u003c '0' || c \u003e '9')\r\n\t\t\t\t\tthrow new InputMismatchException();\r\n\t\t\t\tres *= 10;\r\n\t\t\t\tres += c - '0';\r\n\t\t\t\tc = read();\r\n\t\t\t} while (!isSpaceChar(c));\r\n\t\t\treturn res * sgn;\r\n\t\t}\r\n\r\n\t\tpublic int[] nextIntArray(int n) {\r\n\t\t\tint[] arr = new int[n];\r\n\t\t\tfor (int i = 0; i \u003c n; i++) {\r\n\t\t\t\tarr[i] = nextInt();\r\n\t\t\t}\r\n\t\t\treturn arr;\r\n\t\t}\r\n\r\n\t\tpublic long[] nextLongArray(int n) {\r\n\t\t\tlong[] arr = new long[n];\r\n\t\t\tfor (int i = 0; i \u003c n; i++) {\r\n\t\t\t\tarr[i] = nextLong();\r\n\t\t\t}\r\n\t\t\treturn arr;\r\n\t\t}\r\n\r\n\t\tprivate boolean isSpaceChar(int c) {\r\n\t\t\treturn c == ' ' || c == '\\n' || c == '\\r' || c == '\\t' || c == -1;\r\n\t\t}\r\n\r\n\t\tprivate boolean isEndOfLine(int c) {\r\n\t\t\treturn c == '\\n' || c == '\\r' || c == -1;\r\n\t\t}\r\n\t}\r\n}"},{"language_token":"cpp","language_name":"C++ (g++ 5.4)","solution_code":"#include \u003cbits/stdc++.h\u003e\r\n\r\n#define mod 1000000007\r\n#define inf 2000000000\r\n#define root2 1.41421\r\n#define root3 1.73205\r\n#define pi 3.14159\r\n#define MAX 300001\r\n#define ll long long int\r\n#define PII pair\u003cint, int\u003e\r\n#define f first\r\n#define s second\r\n#define ss(n) scanf(\"%lld\", \u0026n)\r\n#define ssf(n) scanf(\"%lf\", \u0026n)\r\n#define mk make_pair\r\n#define PLL pair\u003cll, ll\u003e\r\n#define gc getchar\r\n#define pb push_back\r\n#define FR(i, a, b) for(ll i=(ll)a;i\u003c=(ll)b;i++)\r\n#define RFR(i, a, b) for(ll i=(ll)a;i\u003e=(ll)b;i--)\r\nusing namespace std;\r\nll Table[MAX][20], A[MAX];\r\n\r\nll gcd(ll a, ll b)\r\n{\r\n    if(b==0)\r\n        return a;\r\n    else\r\n        return gcd(b, a%b);\r\n}\r\nvoid preprocess(ll n)\r\n{\r\n    ll i, j;\r\n    //initialize Table\r\n    FR(i, 0, n-1)\r\n        Table[i][0] = A[i];\r\n    //compute values from smaller to bigger intervals\r\n    for (j = 1; 1 \u003c\u003c j \u003c= n; j++)\r\n        for (i = 0; i + (1 \u003c\u003c j) - 1 \u003c n; i++)\r\n        {\r\n            // j tells how much further to move from i\r\n            if(Table[i][j - 1]\u003eTable[i + (1 \u003c\u003c (j - 1))][j - 1])\r\n                Table[i][j] = gcd(Table[i][j - 1],Table[i + (1 \u003c\u003c (j - 1))][j - 1]);\r\n            else\r\n                Table[i][j] = gcd(Table[i + (1 \u003c\u003c (j - 1))][j - 1], Table[i][j - 1]);\r\n        }\r\n}\r\nll query(ll l, ll r, ll n)\r\n{\r\n    ll k=20;\r\n    ll ans=0;\r\n    ll i;\r\n    RFR(i, k, 0)\r\n    {\r\n        if(l+(1\u003c\u003ci)-1\u003c=r)\r\n        {\r\n            if(ans\u003eTable[l][i])\r\n                ans=gcd(ans, Table[l][i]);\r\n            else\r\n                ans=gcd(Table[l][i], ans);\r\n            l+=1\u003c\u003ci;//increasing l\r\n        }\r\n    }\r\n    return ans;\r\n\r\n}\r\nint main()\r\n{\r\n    ll n, q, l, r, i, ans;\r\n    cin\u003e\u003en;\r\n    FR(i, 0, n-1)\r\n        ss(A[i]);\r\n    preprocess(n);\r\n    ss(q);\r\n    while(q--)\r\n    {\r\n        cin\u003e\u003el\u003e\u003er;\r\n        l--;r--;\r\n        ans=query(l, r, n);\r\n        cout\u003c\u003cans\u003c\u003cendl;\r\n    }\r\n}\r\n\r\n"}]},"message":"Solution For Problem","status":200,"error":null,"disabled":false,"update":false,"external_rating":null}
/*
CREATE TABLE posts (
  post_id INT AUTO_INCREMENT,
  user_id INT,
  postext VARCHAR(255),
  views INT DEFAULT 0,
  -- clicks INT,
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (post_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);
*/

-- CREATE TABLE button

CREATE TABLE likes (
  post_id INT,
  user_id INT,
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (post_id, user_id),
  FOREIGN KEY (post_id) REFERENCES post(post_id),
  FOREIGN KEY (user_id) REFERENCES user_table(user_id)
)

CREATE TABLE clicks (
  post_id INT,
  user_id INT,
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (post_id, user_id),
  FOREIGN KEY (post_id) REFERENCES post(post_id),
  FOREIGN KEY (user_id) REFERENCES user_table(user_id)

)



CREATE TABLE images (
  image_id INT AUTO_INCREMENT,
  -- user_id INT,
  post_id INT,
  image_path VARCHAR(255),
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (image_id),
  -- FOREIGN KEY (user_id) REFERENCES user_table(user_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id)
);


CREATE TABLE comments (
  comment_id INT AUTO_INCREMENT,
  user_id INT,
  post_id INT,
  comment_text VARCHAR(255),
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (comment_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id)
);


CREATE TABLE content_comments (
  comment_id INT AUTO_INCREMENT,
  user_id INT,
  content_id INT,
  comment_text VARCHAR(255),
  created_at TIMESTAMP DEFAULT NOW(),

  PRIMARY KEY (comment_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (content_id) REFERENCES content_posts(content_id)
);


CREATE TABLE pages (
  page_id INT,
  creator_id INT,
  page_name VARCHAR(255),
  views INT,
  clicks INT,

  PRIMARY KEY (page_id),
  FOREIGN KEY (creator_id) REFERENCES creator(creator_id)
);


/*
DatabaseHandler handler = null;

public LoginForm() {
    initComponents();
    handler = DatabaseHandler.getInstance();
}
*/


CREATE TABLE friends (
  user1 INT,
  user2 INT,
  	created_at TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (user1, user2),
  FOREIGN KEY (user1) REFERENCES users(user_id),
  FOREIGN KEY (user2) REFERENCES users(user_id)
)

CREATE TABLE friend_requests (
  user_from INT,
  user_to INT,
	created_at TIMESTAMP DEFAULT NOW(),
  PRIMARY KEY (user_from, user_to),
  FOREIGN KEY (user_from) REFERENCES users(user_id),
  FOREIGN KEY (user_to) REFERENCES users(user_id)
)

CREATE TABLE messages (
  message_id INT PRIMARY KEY AUTO_INCREMENT,
  user_from INT,
  user_to INT,
  message_text VARCHAR (255),
  created_at TIMESTAMP DEFAULT NOW(),

  FOREIGN KEY (user_from) REFERENCES users(user_id),
  FOREIGN KEY (user_to) REFERENCES users(user_id)
)

CREATE TABLE notifications (
  notification_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  notification_text VARCHAR (255),
  created_at TIMESTAMP DEFAULT NOW(),

  FOREIGN KEY (user_id) REFERENCES users(user_id),
)

CREATE TABLE trends (
  trend_id INT PRIMARY KEY AUTO_INCREMENT,
  trend_text VARCHAR(255),
  created_at TIMESTAMP DEFAULT NOW(),
)

INSERT INTO `content_posts` (`content_id`, `belongs_to_page_id`, `post_text`, `like_count`, `view_count`, `image_path`, `created_at`) VALUES (NULL, '1', 'Even though you don\'t tell them, but you surely love them a lot!\r\nThis SiblingsDay, tag your siblings and let them know how much you love them!', '0', '0', 'default_post_pic.png', CURRENT_TIMESTAMP);
INSERT INTO `content_show_page` (`content_id`, `show_to_page_id`) VALUES ('1', '1');

INSERT INTO `content_posts` (`content_id`, `belongs_to_page_id`, `post_text`, `like_count`, `view_count`, `image_path`, `created_at`) VALUES (NULL, '2', 'Kalank Out Next Week! Watch it with Everyone!', '0', '0', 'default_post_pic.png', CURRENT_TIMESTAMP);


SELECT * FROM pages p INNER JOIN content_posts cp ON p.page_id = cp.belongs_to_page_id WHERE user_id = ?

-- selecting all content Generic Page Populated
SELECT *
FROM  (content_show_page csp INNER JOIN content_posts cp
      ON csp.content_id = cp.content_id) INNER JOIN pages p ON p.page_id = cp.belongs_to_page_id
WHERE csp.show_to_page_id = ? -- current Page

-- Red Chillies Page Populated (shows badla and kalankAd)
  SELECT * FROM  (content_show_page csp INNER JOIN content_posts cp ON csp.content_id = cp.content_id) INNER JOIN pages p ON p.page_id = cp.belongs_to_page_id WHERE csp.show_to_page_id = 1; --

-- Dharma Page Populated (shows kalank only)
SELECT *
FROM  (content_show_page csp INNER JOIN content_posts cp
      ON csp.content_id = cp.content_id) INNER JOIN pages p ON p.page_id = cp.belongs_to_page_id
WHERE csp.show_to_page_id = 2; --
