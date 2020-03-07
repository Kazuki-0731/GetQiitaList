# Qiita記事一覧アプリ
Kotlinで制作したQiita記事一覧をListで表示し、記事詳細をWebViewで表示するアプリです。

## [アプリアイコン]
<p align="center">
<img src="https://user-images.githubusercontent.com/28224709/76067227-62962780-5fd2-11ea-8d44-4168ace65eed.jpg" width="50%">
</p>

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
Androidプロジェクトのフォルダ構成は、MVPアーキテクチャになっております。

![Model](https://user-images.githubusercontent.com/28224709/76072118-bf95db80-5fda-11ea-8edf-4556ed8eba89.png)

### Presenter(通信周り)
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

## [実際の画面]
### ロディングアイコン表示
<img src="https://user-images.githubusercontent.com/28224709/76140748-de5ea580-60a0-11ea-9559-67b6158bb44f.jpg" width="50%">

### Qiita記事一覧表示
<img src="https://user-images.githubusercontent.com/28224709/76140745-dbfc4b80-60a0-11ea-8bb7-776482165f02.jpg" width="50%">

### 詳細画面へ遷移(ListViewの1セルを押下)
<img src="https://user-images.githubusercontent.com/28224709/76140739-d4d53d80-60a0-11ea-9a3a-33b48f654c8a.jpg" width="50%">

### サーバーから200番台以外の、ステータスコードを受け取った場合のエラー
<img src="https://user-images.githubusercontent.com/28224709/76140742-da328800-60a0-11ea-92b6-34571000d8c9.jpg" width="50%">

### サーバー通信中に発生したエラー
<img src="https://user-images.githubusercontent.com/28224709/76140750-def73c00-60a0-11ea-87ec-ac71b6baa1ef.jpg" width="50%">

### 不明なエラー
<img src="https://user-images.githubusercontent.com/28224709/76140751-df8fd280-60a0-11ea-90b6-f23ff7e37af3.jpg" width="50%">

### 詳細画面にて、通信エラー
<img src="https://user-images.githubusercontent.com/28224709/76140740-d868c480-60a0-11ea-824f-c615319039a1.jpg" width="50%">

