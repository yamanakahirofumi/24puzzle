# アーキテクチャ設計

本プロジェクトでは、保守性と拡張性を高めるため、標準的な MVC (Model-View-Controller) パターンを採用します。

## 1. ディレクトリ・パッケージ構造
標準的な Maven 構造および JavaFX のモジュール・システム（JPMS）に準拠した構成を推奨します。

```
.
├── pom.xml                # プロジェクト構成 (Maven)
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── module-info.java  # モジュール定義
│   │   │   └── com.example.puzzle
│   │   │       ├── Main.java     # エントリーポイント
│   │   │       ├── model/        # ビジネスロジック
│   │   │       ├── view/         # JavaFX UI コンポーネント
│   │   │       ├── controller/   # ユーザー入力制御
│   │   │       ├── service/      # 画像処理・アルゴリズム
│   │   │       └── util/         # ユーティリティ
│   │   └── resources
│   │       └── com.example.puzzle
│   │           ├── images/       # アイコン・デフォルト画像
│   │           └── css/          # スタイルシート
│   └── test
│       └── java
│           └── com.example.puzzle # ユニットテスト
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

## 3. 構成ファイルの定義

本プロジェクトは Java モジュール・システム（JPMS）を利用します。主要な構成ファイルの定義例を以下に示します。

### 3.1 module-info.java
```java
module com.example.puzzle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens com.example.puzzle to javafx.fxml;
    opens com.example.puzzle.controller to javafx.fxml;
    exports com.example.puzzle;
}
```

### 3.2 pom.xml (依存関係)
```xml
<dependencies>
    <!-- JavaFX -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 4. デザイン方針
- **単一責任の原則 (SRP)**: 各クラスは一つの明確な役割を持つように設計します。
- **不変性 (Immutability)**: 可能な限り、状態を変更するのではなく新しいオブジェクトを生成するアプローチ（特にデータクラスにおいて）を検討します。
- **JavaFX のスレッドルール**: UI の更新は常に JavaFX Application Thread で行うように徹底します。重い処理（画像加工など）はバックグラウンドスレッドで行います。
