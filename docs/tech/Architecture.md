# アーキテクチャ設計

本プロジェクトでは、保守性と拡張性を高めるため、標準的な MVC (Model-View-Controller) パターンを採用します。

## 1. パッケージ構造
推奨されるパッケージ構造は以下の通りです。

```
com.example.puzzle
├── Main.java             # エントリーポイント
├── model                 # ビジネスロジックとデータ構造
│   ├── PuzzleBoard.java  # 盤面の状態管理
│   ├── Tile.java         # 各パネルのデータ
│   └── GameState.java    # ゲームの進行状態 (PLAYING, SOLVED 等)
├── view                  # ユーザーインターフェース (JavaFX)
│   ├── MainView.java     # メイン画面のレイアウト
│   └── TileView.java     # 各パネルの描画
├── controller            # ユーザー入力の制御
│   └── GameController.java
├── service               # 補助的な機能
│   ├── ImageService.java # 画像の読み込み・加工
│   └── ShuffleService.java # シャッフルアルゴリズム
└── util                  # 共通ユーティリティ
    └── SolvabilityChecker.java
```

## 2. 主要クラスの責務

### 2.1 Model 層
- **PuzzleBoard**: 5×5 のグリッド（2次元配列または1次元配列）を保持し、パネルの移動ロジック（隣接判定、入れ替え）を担当します。
- **Tile**: パネルが持つべき情報（本来のインデックス、現在の位置、画像データ等）を保持します。

### 2.2 View 層
- **MainView**: JavaFX の `GridPane` や `BorderPane` を使用し、全体のレイアウトを構築します。
- **TileView**: 各 `Tile` を `StackPane` や `ImageView` として描画し、移動時のアニメーションを担当します。

### 2.3 Controller 層
- **GameController**: ビューからのイベント（ボタンクリック、パネルクリック）を受け取り、モデルの状態を更新し、ビューに反映させます。

### 2.4 Service 層
- **ImageService**: ユーザーが選択した画像を 5×5 に等分割し、各パネル用の `Image` オブジェクトを生成します。アスペクト比を維持するためのクロップ処理もここで行います。
- **ShuffleService**: パズルをシャッフルします。単なるランダムな入れ替えではなく、必ず解ける状態を生成することを保証します。

## 3. デザイン方針
- **単一責任の原則 (SRP)**: 各クラスは一つの明確な役割を持つように設計します。
- **不変性 (Immutability)**: 可能な限り、状態を変更するのではなく新しいオブジェクトを生成するアプローチ（特にデータクラスにおいて）を検討します。
- **JavaFX のスレッドルール**: UI の更新は常に JavaFX Application Thread で行うように徹底します。重い処理（画像加工など）はバックグラウンドスレッドで行います。
