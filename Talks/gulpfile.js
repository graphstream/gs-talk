var gulp = require('gulp');
var sass = require('gulp-sass');
var pandoc = require('gulp-pandoc');
var concat = require('gulp-concat');
var copy = require('gulp-copy');
var browserSync = require('browser-sync');
var reload = browserSync.reload;
var del = require('del');
var prefix = require('gulp-autoprefixer');
var svgmin = require('gulp-svgmin');
var gutil = require('gulp-util');

var paths = {
  md: ['src/**.md'],
  scss: ['src/css/*.scss'],
  css: 'src/css/*.css',
  bitmaps: ['src/img/**/*', '!src/img/**/*.svg'],
  svg: 'src/img/**/*.svg',
  fonts: 'src/fonts/**/*',
  dist: 'app'
};
gulp.task('copy', function() {
  return gulp.src(paths.bitmaps.concat( paths.fonts))
  .pipe(copy(paths.dist, {prefix:1}));
});

gulp.task('svg', function() {
  return gulp.src([paths.svg])
  .pipe(svgmin())
  .pipe(gulp.dest(paths.dist+'/img'));
  });

gulp.task('sass', ['clean-css'],function() {
  return gulp.src(paths.scss)
    .pipe(sass( {
      includePaths: [
        'app/bower_components/reveal.js/css/theme/source',
        'src/css'],
        errLogToConsole: true
      }).on('error', sass.logError))
    .pipe(prefix('last 5 versions', '> 1%'))
    .pipe(concat('main.css'))
    .pipe(gulp.dest(paths.dist+'/css/'))
    .pipe(reload({ stream:true }));
});

gulp.task('clean-css', function() {
    return del([paths.dist+'/css/main.css']);
});

gulp.task('clean', function() {
    return del([paths.dist+'/*', '!'+paths.dist+'/bower_components']);
});

gulp.task('pandoc', function() {
  return gulp.src(paths.md)
    .pipe(pandoc({
      from: 'markdown',
      to: 'revealjs',
      ext: '.html',
      args: ['--smart',
        '--standalone',
        '--section-divs',
        '--css=css/main.css',
        // '--template=pandoc-templates/default.revealjs',
        '-V',
        'revealjs-url=bower_components/reveal.js'
      ]
    }))
    .pipe(gulp.dest(paths.dist))
    .pipe(reload({ stream:true }));
});



// watch files for changes and reload
gulp.task('serve', function() {
  browserSync({
    server: {
      baseDir: paths.dist
    }
  });

  gulp.watch(paths.scss, ['sass']);
  gulp.watch(paths.md, ['pandoc']);
  gulp.watch(paths.svg, ['svg']);

});

gulp.task('default', [ 'sass', 'pandoc', 'copy', 'svg']);
