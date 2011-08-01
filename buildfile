repositories.remote << 'http://www.ibiblio.org/maven2'

JSOUP='org.jsoup:jsoup:jar:1.6.1'
MAIN = 'ca.junctionbox.sso.TimeApp'

define 'te' do
	project.version = '0.1.0'

	run.using :main => MAIN

	compile.using(:lint => 'all').with(JSOUP)

	manifest['Main-Class'] = MAIN
	package(:jar).merge(JSOUP)

	test.using(:testng)
end
