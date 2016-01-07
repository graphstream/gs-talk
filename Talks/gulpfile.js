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

var paths = {
  md: ['src/**.md'],
  scss: ['src/css/*.scss'],
  css: 'src/css/*.css',
  bitmaps: ['src/img/**/*', '!src/img/**/*.svg', '!src/img/**/*.graffle'],
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
  .pipe(svgmin({plugins: [{
      cleanupIDs: false
    },
    {convertStyleToAttrs:false
  }]}))
  .pipe(gulp.dest(paths.dist+'/img'));
  });

gulp.task('sass', ['clean-css'],function() {
  return gulp.src(paths.scss)
    .pipe(sass( {
      includePaths: [
        'app/bower_components/reveal.js/css/theme/source',
        'src/css'],
        errLogToConsole: true,
        outputStyle: 'compressed'
      }).on('error', sass.logError))
    .pipe(prefix('last 5 versions', '> 1%'))
    .pipe(concat('main.css'))
    .pipe(gulp.dest(paths.dist+'/css/'))
    .pipe(browserSync.stream());

});

gulp.task('clean-css', function() {
    return del([paths.dist+'/css/main.css']);
});

gulp.task('clean', function() {
    return del([paths.dist+'/*', '!'+paths.dist+'/bower_components', '!'+paths.dist+'/.git']);
});


gulp.task('pandoc-watch', ['pandoc'], reload);

gulp.task('pandoc', function() {
  return gulp.src(paths.md)
    .pipe(pandoc({
      from: 'markdown',
      to: 'revealjs',
      ext: '.html',
      args: ['--smart',
        '--standalone',
        //'--section-divs',
        '--css=css/main.css',
        '--mathjax',
        // '--template=pandoc-templates/default.revealjs',
        '-V',
        'revealjs-url=bower_components/reveal.js'
      ]
    }))
    .pipe(gulp.dest(paths.dist));
});



// watch files for changes and reload
gulp.task('serve', function() {
  browserSync({
    server: {
      baseDir: paths.dist,
      ghostMode:true
    }
  });

  gulp.watch(paths.scss, ['sass']);
  gulp.watch(paths.md, ['pandoc-watch']);
  gulp.watch(paths.svg, ['svg']);

});

gulp.task('default', [ 'sass', 'pandoc', 'copy', 'svg']);
