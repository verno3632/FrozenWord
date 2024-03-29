# GitHub Actions の job のステータスを受け取る
job_status = ENV['JOB_STATUS']

# 追加・変更していないコードはコメント対象外とするか
github.dismiss_out_of_range_messages({
  error: false, # エラーは追加・変更していないコードでもコメント
  warning: true,
  message: true,
  markdown: true
})

# ktlint の結果ファイルの解析とコメント
begin
  checkstyle_reports.inline_comment=true
  checkstyle_reports.report_method=:warn

  Dir.glob("**/ktlint*Check.xml").each do |xml|
    checkstyle_reports.report(xml, modified_files_only: true)
  end
end

# Android Lint の結果ファイルの解析とコメント
android_lint.skip_gradle_task = true # 既にある結果ファイルを利用する
android_lint.filtering = false # エラーは追加・変更したファイルでなくてもコメント
Dir.glob("**/lint-results*.xml").each do |xml|
    android_lint.report_file=xml
    android_lint.lint(inline_mode: true)
end


# 最終結果でレポートするワーニング数は Android Lint と ktlint のみの合計としたいのでここで変数に保存
lint_warning_count = status_report[:warnings].count

# Local unit test の結果ファイルの解析とコメント
Dir.glob("**/build/test-results/*/*.xml").each { |report|
  junit.parse report
  junit.show_skipped_tests = true # スキップしたテストをワーニングとする(状況により適宜変更)
  junit.report
}

# プルリクの body が空の場合はエラー
fail 'Write at least one line in the description of PR.' if github.pr_body.length < 1

# プルリクが大きい場合はワーニング
warn 'Changes have exceeded 500 lines. Divide if possible.' if git.lines_of_code > 500

# 追加で独自のチェックをする場合はこのあたりで実施する
# ...

# Danger でエラーがある場合は既に何かしらコメントされているのでここで終了
return unless status_report[:errors].empty?

# GitHub Actions のワークフローのどこかでエラーがあった場合はその旨をコメントして終了
return markdown ':heavy_exclamation_mark:Pull request check failed.' if job_status != 'success'

# 成功時のコメント(もし不要な場合は省いてもいいと思います)
comment = ':heavy_check_mark:Pull request check passed.'
if lint_warning_count == 0
  markdown comment
else
  # ktlint と Android Lint のワーニング数の合計をレポート
  markdown comment + " (But **#{lint_warning_count}** warnings reported by Android Lint and ktlint.)"
end

system("./gradlew ktlintFormat")
suggester.suggest
