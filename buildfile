JSOUP='org.jsoup:jsoup:jar:1.6.1'
TIME_MAIN = 'ca.junctionbox.sso.TimeApp'
RM_MAIN = 'ca.junctionbox.sso.ResourceApp'

define 'cas-sso' do
	project.version = '0.1.0'
	test.using(:testng)

	define 'shared' do
		compile.using(:lint => 'all').with(JSOUP)
		package(:jar).merge(JSOUP)
	end

	define 'te' do
		compile.using(:lint => 'all').with(JSOUP, project('shared'))
		run.using :main => TIME_MAIN

		manifest['Main-Class'] = TIME_MAIN
		package(:jar).merge(project('cas-sso:shared').packages.select { |pkg| pkg.type == :jar })
	end

	define 'rm' do
		compile.using(:lint => 'all').with(JSOUP, project('shared'))
		run.using :main => RM_MAIN

		manifest['Main-Class'] = RM_MAIN
		package(:jar).merge(project('cas-sso:shared').packages.select { |pkg| pkg.type == :jar })
	end
end
