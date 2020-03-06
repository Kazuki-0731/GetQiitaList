# Qiita記事一覧アプリ
Kotlinで制作したQiita記事一覧をListで表示し、記事詳細をWebViewで表示するアプリです。

## [アプリアイコン]
<img src="https://user-images.githubusercontent.com/28224709/76067227-62962780-5fd2-11ea-8d44-4168ace65eed.jpg" width="50%">

## [機能説明]
* 画面全体をリスト表示しています。
* アプリ起動後、Qiita APIを利用して対象アカウント「susu_susu__」の記事を一覧取得して、ListViewで表示します。
* 初期データは空です。
* 表示する順序は、記事作成日時の降順です。
* 画面を上からスワイプすると、再読み込みが発生します。
* ListViewをタップすると詳細画面へ遷移します。
* 通信エラー時は、エラー種別によってダイアログを表示します。

## [主な仕様]
* Qiita記事一覧リストに表示な最大件数
  * 100件
* アラート
  * ステータスコードに関するエラー場合、「通信エラーが発生しました」と表示
  * サーバー通信に関するエラー場合、「通信環境に不具合が生じています。時間をおいて、再度お試しください」と表示
  * 上記以外の場合、「不明なエラーが発生しました」と表示

## [プロジェクト構成]
Androidプロジェクトのフォルダ構成は、MVCアーキテクチャになっております。

Controllerに関しては、ユーザーからの入力が特に無いため、特別設けていません。

### Model構成
* action
  * 設定系
* database
  * SQLiteのデータベース周り
* entities
  * キャッシュ(一時保存)

![Model](https://user-images.githubusercontent.com/28224709/68700424-3d674e80-05c8-11ea-9679-a672599165a6.png)

### View構成
* common
  * フロントに共通化する処理や定数など
* fragment
  * fragment関係
* util
  * UIオブジェクトのカスタムクラス郡

![Veiw](https://user-images.githubusercontent.com/28224709/68700445-448e5c80-05c8-11ea-85f4-50bb43d75bb0.png)

## [各アイコンの説明]
* 右下のアイコン押下でダイアログ表示
  * ダイアログ内にあるテキストボックス入力でDB内にデータ挿入
  * キャンセルでダイアログを閉じる
* 中央のアイコン押下で全表示/Active/Inactiveを切り替えます
  * 全表示の場合、三本線のアイコンになります
    * 全タスク表示されます
  * Activeの場合、黄色い星アイコンになります
    * 完了タスクのみ表示されます
  * Inactiveの場合、グレーの星アイコンになります
    * 未完了タスクのみ表示されます
* リストの右側のゴミ箱アイコン押下で１行削除します
  * 削除されると画面がリロードします

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

