# 概要
LINEヤフー株式会社（当時LINE株式会社）様が2021年の新卒採用試験として出題されたタクシーの運賃計算アプリのコーディングテストの問題[^1]をもとに、これまでJPIN[^2]で学んできたオブジェクト指向設計を実践してみた

簡単なフローチャートや概念モデリングの図とともに、この設計になった経緯やつまづきポイント、所感等を書き留めていく

[^1]:[LINEの新卒採用試験ズバリ問題解説 〜2021年開発コース(実装問題)版〜](https://engineering.linecorp.com/ja/blog/commentary-of-coding-test-2021)
[^2]:[JPIN – 就職・転職を決断する前に、人生の可能性を知ろう](https://www.jpin.info/)

## バージョンについて
コミットに対してバージョンを示すタグをつける  
表記は verX.Y という形式で示す  
+  X:設計をやり直した際に数字を上げる  
+  Y:設計やコードの修正をした際に数字を上げる  


## ver1.0について
<img width="1210" alt="スクリーンショット 2024-03-30 20 36 07" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/ea5a696e-7e00-4fb4-bd7c-e7f1aa675262">
大雑把な処理の流れを書いてみたら結果これがmainメソッドのコードになった
　
<img width="1089" alt="スクリーンショット 2024-03-30 20 47 31" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/875ebd22-8698-43df-80a7-259e29ea359a">
「運賃はレコード、課金ルール、基本料金、時間帯による割増率によって決まる」ということを表現しようとした結果このような概念モデルになっている

「エリック・エヴァンスのドメイン駆動設計」[^3]を読んでいる中で「サービス」というものを知り、「初乗運賃」「距離運賃」「低速走行時間運賃」を計算するものはレコードの扱い方のルールを定めるだけで属性は持たなくていいだろうという考えからそれぞれの運賃を計算するサービスクラスを設けてみた
[^3]:[エリック・エヴァンスのドメイン駆動設計 著者:Eric Evans](https://www.amazon.co.jp/%E3%82%A8%E3%83%AA%E3%83%83%E3%82%AF%E3%83%BB%E3%82%A8%E3%83%B4%E3%82%A1%E3%83%B3%E3%82%B9%E3%81%AE%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88-Eric-Evans-ebook/dp/B00GRKD6XU/ref=sr_1_4?__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&crid=3EVGCL152U1J3&dib=eyJ2IjoiMSJ9.GC2YstolP1GEYBmLG4WPACNFOr34GrhRHTN3Hzra9CCYgnsGyBr8cE24IwwmSZ9e3sduvG-1hEnM5NKD2I4fuTFqe6WDawfhv8k-49a6BY5qfLdUgLYCPPVKMMccXwuEEHB-JMfd8nt09NyT7gl0Q5WnWIQ1XDkMmO5xmSSgF1gyILS4GFbJvFriq56OKU7FFf0HbLUEuBn5im32Ozzwfq03kVUb2rr4E9P-m86URTg.MPd2kt_bfckwwmQ43cDPhPey8NVEEct-OVBga7ViNTc&dib_tag=se&keywords=%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88&qid=1711804733&s=books&sprefix=%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88%2Cstripbooks%2C196&sr=1-4)

ただし「初乗運賃」「距離運賃」「低速走行時間運賃」のそれぞれのルールの違いがここからはわからないので正しい概念モデリングとは言えないだろうという感じがしている

また低速走行時間運賃を計算する処理の中で総低速走行時間を求める際に、時刻と距離を持っているRecordを扱っているRecordListクラスの中で総低速走行時間を求めるメソッドを持たせているが、そのせいで「時速10km以下の時に低速走行時間メーターが加算される」というルールがRecordListクラスに定義されてしまっている

### その他の気になりポイント  
+ メソッド内に数値がベタ書きになっているのが気になっている（この段階ではそこまで考えなくていよい？）
  + よくよく見返すと動作確認のためだけに作った不要なメソッドが残されていた、、、
+ 処理内容がわかるようなメソッド名になるよう注意したが、引数の与え方には考慮の余地がまだまだありそう

### 今後やりたいこと
+ ビジネスルールを適切なクラスにまとめたい
+ 各種運賃計算の仕組みがわかる概念モデルに修正する
+ 安心してリファクタリングできるようにするためのテストコードを書く
+ パッケージ、モジュールシステムを使ったアクセス制限

## ver2.0について
このバージョンはコーディング途中で設計を見直すこととなったので、バグが残ったままになっている  

<img width="320" alt="スクリーンショット 2024-03-31 10 19 56" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/88bbf7a8-6ff9-4a03-bd33-aaa1273366ed">
<img width="572" alt="スクリーンショット 2024-03-31 10 20 13" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/3b9e8723-e70d-4814-a19e-adba8c73b2a2">
<img width="585" alt="スクリーンショット 2024-03-31 10 22 00" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/7c19b888-4305-4011-a290-d0d571d107ff">
<img width="487" alt="スクリーンショット 2024-03-31 10 20 41" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/327e96e3-c83f-437c-bb3a-9d9d0c6be6c0">

ver1.0の際の概念モデルでは「初乗運賃」「距離運賃」「低速走行時間運賃」のそれぞれのルールの違いがわからなかったため各運賃計算ごとのモデリングを行った  
各運賃計算ごとにモデリングを行ったのは次の2つの理由のため  
+ これらを一つにまとめようとすると「走行距離」「時刻」「時間帯」「基本料金」「割増率」などが重複して登場するのでわかりにくい
+ モデリングする範囲を狭めてモデルや実装をシンプルにしたい

### コーディングした結果
「低速走行時間運賃」を計算するために必要な「総低速走行時間」は、初乗区間が定額なため初乗区間を過ぎたところから計測するが、「総走行距離」をデータとして扱うクラスがないため、「総低速走行時間」を取得するコードが複雑（というより無理矢理？）でわかりにくくなった。ここで「総走行距離」や「総低速走行時間」を扱うクラスが必要なのではと思い始める  
(問題として定めてある仕様には「運賃メーター」「距離メーター」「低速走行時間メーター」という言葉が登場している。最初は「時刻」「走行距離」を扱う「レコード」と各種運賃計算サービスがあれば各種メーターの概念がなくとも導出できると考えていたため、「総走行距離」や「総低速走行時間」を扱う目的のクラスを設けていなかった)

### その他実施したこと
+ クラス名、メソッド名の変更や切り分け方を変更
+ Recordクラスに不要なメソッドがあったため、それら含め整理した
+ RecordListクラスに持たせていた運賃計算ルールに関わる処理を各サービスに移動
  + 例えば低速走行であったかを判断するメソッドを「低速走行時間運賃計算サービス」に移動させた
+ コーディングの際の参考になるかと概念モデルに対して多重度の追加や「単一」「複合」のデータであるかの整理を一部行なった
+ 概念モデルにおいて最初「時間帯」を「割増率」に関連させていたが時間帯は最初「割増率」に紐づけていたが、「時刻を持っているレコードで扱った方が良さそう」、「アプリ全体として時間帯の区切りが各種メーターごとに定義が異なるということはなさそう」という理由で「レコード」が持つ「時刻」に関連させるという修正をした(以下画像参考) 
<img width="1115" alt="スクリーンショット 2024-03-31 10 57 11" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/d5ba7139-f789-44af-aa56-6e61787a3424">

### 設計の見直しポイント
「運賃メーター」「距離メーター」「低速走行時間メーター」という概念を取り入れてみる。これによりServiceクラスではなくEntityクラスが生まれるのではないかと予想している。  
また「初乗運賃」「距離運賃」「低速走行時間運賃」を切り分けて概念モデリングを行ってきたが、これらをまとめて概念モデリングができないか再考してみる

## ver3.0について
ver2.0の反省から「総走行距離」や「総低速走行時間」を扱う「距離メーター」、「低速走行時間メーター」クラスを登場させた  
まず処理の流れから整理した
<img width="885" alt="スクリーンショット 2024-03-31 21 04 15" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/d73ba973-df0d-4597-9ea0-7e4f666c4a3f">
ver2.0では「総低速走行時間」を取得するルールをうまく表現できなかったので、レコードを受け取ってから「距離メーター」と「低速走行時間メーター」に記録するところまでをスコープにしてコードを書いてみた  
↓その時に書いたコードをコミットしていなかったので簡単なクラス図で書くとこんな感じ
<img width="447" alt="スクリーンショット 2024-03-31 23 51 38" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/4cbc0f8f-7848-4beb-85c9-ca2e9354aa6e">


入力で受け取ったレコードを「距離メーター」「低速走行時間メーター」それぞれに記録し「低速走行時間メーター」には初乗区間をまたぐレコードから記録する条件にすれば、それぞれのメーターから「距離運賃」「低速走行時間運賃」を求めることができそうと考えていた  
  
しかし結局総走行距離(距離メーター)が分からなければ総低速走行時間を計測することができなかった  
→例えば出発から950m地点から次のレコードで1050mに進んだとき、1000m地点から1050m地点の走行速度を求めて低速走行だったかどうかを判断したかったが、「低速走行時間メーター」は「距離メーター」を知らないのでどの地点から1050m地点に来たか知ることができない  
  
↓そこで見直した処理の流れがこちら  
<img width="659" alt="スクリーンショット 2024-03-31 23 27 05" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/6acd9530-1909-4261-9b72-f69bfaacc9ea">

運賃計算はとりあえず置いといて「距離メーター」と「低速走行時間メーター」を記録する仕組みをまずコーディングしながら整理した  
これによって「低速走行時間メーター」は「距離メーター」を参照する形にしたのでレコードを持たない形になった  

↓こちらがその概念モデル（概念モデルとして正しい表現方法になっているかちょっと自信がないが、、、）
<img width="597" alt="スクリーンショット 2024-04-02 0 25 12" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/e0129a7d-998f-4bc1-888e-67e7a825ff42">



↓この概念モデルに各運賃計算の仕組みを加えて表現したのがこちら
<img width="659" alt="スクリーンショット 2024-03-31 23 42 44" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/6f65bedd-fa55-4647-8200-ec99a6ee5627">

↓概念モデルを元にコーディングして整理したver3.0のクラス図(厳密なクラス図とはいえないと思うが、、、)
<img width="1068" alt="スクリーンショット 2024-04-02 0 24 19" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/333cff68-a28d-4506-a74f-031d474afc7d">


