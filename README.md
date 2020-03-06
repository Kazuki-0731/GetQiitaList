# Qiita記事一覧アプリ
Kotlinで制作したQiita記事一覧をListで表示し、記事詳細をWebViewで表示するアプリです。

## [アプリアイコン]
<img src="https://user-images.githubusercontent.com/28224709/76067227-62962780-5fd2-11ea-8d44-4168ace65eed.jpg" width="50%">

## [機能説明]
* アプリ起動後、Qiita APIを利用して対象アカウント「susu_susu__」の記事を一覧取得して、ListViewで表示します。
* 画面を上からスワイプすると、再読み込みが発生します。
* ListViewをタップすると詳細画面へ遷移します。
* 通信エラー時は、エラー種別によってダイアログを表示します。

## [詳細な仕様]
* Qiita記事一覧リストに表示な最大件数
  * 100件
* Qiita記事一覧の表示順序
  * 表示する順序は、記事作成日時の降順です。
* 初期データ
  * 初期のListViewのデータは空です。
* 読み込み中(アプリ起動後、詳細画面から復帰時)
  * ローディングアイコンが画面中央に表示されます。
  * 読み込みが完了すると非表示になります。
  * タイムアウト、HTTPエラーが発生した場合は、非表示になります。
* スワイプアイコン
  * 上から画面をスワイプするとAndroid標準のスワイプローディングアイコンが表示されます。
  * 読み込みが完了すると非表示になります。
  * タイムアウト、HTTPエラーが発生した場合は、非表示になります。
* アラート
  * ステータスコードに関するエラー場合、「通信エラーが発生しました」と表示
  * サーバー通信に関するエラー場合、「通信環境に不具合が生じています。時間をおいて、再度お試しください」と表示
  * 上記以外の場合、「不明なエラーが発生しました」と表示

## [プロジェクト構成]
Androidプロジェクトのフォルダ構成は、MVCアーキテクチャになっております。

Controllerに関しては、ユーザーからの入力が特に無いため、特別設けていません。

### 通信周り(http)
* client
  * 通信における正常系、異常系のキャッチをする
* constants
  * 通信時のパラメータ定数
* exception
  * 例外区分、エラーメッセージなど
* repository
  * retrofit2の標準クラスCallで取得(今回は利しないが、通信の勉強の際に利用した)

### 汎用機能
* util
  * ビルド時に生成させる定数取得やLogなどの汎用的なもの

### Model構成
* entities
  * キャッシュ(一時保存)
* service
  * JSONの対象キーを指定して、変数化するための取り決め(プロトコル)

### View構成
* activity
  * activity関係
* adapter
  * ListViewのアダプター関係
* dialog
  * ダイアログ関係
* fragment
  * fragment関係

![Model](https://user-images.githubusercontent.com/28224709/76072118-bf95db80-5fda-11ea-8edf-4556ed8eba89.png)

## [実際の画面]
### 入力画面
<img src="https://user-images.githubusercontent.com/28224709/68698753-f592f800-05c4-11ea-93c8-96a16ff58b19.jpg" width="50%">

### データ挿入後
<img src="https://user-images.githubusercontent.com/28224709/68697851-0478ab00-05c3-11ea-82ff-dd0e1b969001.jpg" width="50%">

### 未完了タスクのみ表示(中央のボタン押下)
<img src="https://user-images.githubusercontent.com/28224709/68697825-f88ce900-05c2-11ea-90f7-bdd208a15fa0.jpg" width="50%">

### 完了タスクのみ表示(中央のボタン押下)
<img src="https://user-images.githubusercontent.com/28224709/68697814-f1fe7180-05c2-11ea-8c3a-daee6f8e37a3.jpg" width="50%">

### すべてのタスク表示(中央のボタン押下)
<img src="https://user-images.githubusercontent.com/28224709/68697851-0478ab00-05c3-11ea-82ff-dd0e1b969001.jpg" width="50%">

### 最大入力文字数を超えた場合
<img src="https://user-images.githubusercontent.com/28224709/68698720-e318be80-05c4-11ea-84c0-8a58afd0284c.jpg" width="50%">

### TODOリストに登録可能な最大件数を超えた場合
<img src="https://user-images.githubusercontent.com/28224709/68698741-f0ce4400-05c4-11ea-97c0-d6657a955e30.jpg" width="50%">

### 対象のTODO項目を削除する場合
<img src="https://user-images.githubusercontent.com/28224709/68697786-e448ec00-05c2-11ea-8231-54c4724b06e5.jpg" width="50%">

### 0桁で入力した場合
<img src="https://user-images.githubusercontent.com/28224709/68699307-1e67bd00-05c6-11ea-8e90-432f0fef0ebf.jpg" width="50%">

