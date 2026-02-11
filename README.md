# 24-Puzzle

## プロジェクト概要
本プロジェクトは、Java と JavaFX を使用した 5×5 のスライドパズル（24-Puzzle）アプリケーションです。
標準的な数字パズルに加え、ユーザーが選択した画像を分割してパズルとして遊べる機能を備えています。

## 主な機能
- **5×5 スライドパズル**: 1から24までのパネルを並べ替えるクラシックなゲームプレイ。
- **画像パズルモード**: ローカルの画像を読み込み、パズルとして利用可能。
- **解の保証**: 必ずゴール可能な初期配置を生成。
- **計測機能**: 手数カウントおよびタイマー機能を搭載。

## ドキュメント構成
詳細な仕様や技術的な設計については、以下のドキュメントを参照してください。

### [機能・仕様 (docs/features/)](docs/features/)
- [ゲームルール](docs/features/Game-Rule.md): 遊び方と勝利条件
- [UI/UX 仕様](docs/features/UI-UX-Specification.md): 画面構成と操作方法

### [技術・開発設定 (docs/tech/)](docs/tech/)
- [技術スタック](docs/tech/Tech-Stack.md): 使用言語・ライブラリ
- [アーキテクチャ設計](docs/tech/Architecture.md): クラス構成と責務
- [実装詳細](docs/tech/Implementation-Details.md): アルゴリズムや画像処理
- [CI 設定](docs/tech/CI-Setting.md): 自動ビルド・テスト環境
- [品質・テスト方針](docs/tech/Quality-Policy.md): テストルールと品質目標

---
詳細は [docs/README.md](docs/README.md) も併せて参照してください。
