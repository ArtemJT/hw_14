# ДЗ 14. Observer implementation


Реализовать паттерн Наблюдатель на собственную тематику или допилить вариант, который был рассмотрен на занятии.
#
Я делал одно задание по паттерну Observer. На тему создания репозитория Git.


Интерфейс [Repository](src/ua/hillel/observer/git/Repository.java):

- Branch getBranch(String name) - возвращает ветку по имени
- Branch newBranch(Branch sourceBranch, String name) - cоздает и возвращает новую ветку
- Commit commit(Branch branch, String author, String[] changes) - создает коммит на данную ветку
- void merge(Branch sourceBranch, Branch targetBranch) - объединяет исходную ветку с целевой веткой
- void addWebHook(WebHook webHook) - добавляет WebHook 


Интерфейс [WebHook](src/ua/hillel/observer/git/WebHook.java) - наблюдает за событиями создания коммитов и слияний между ветвями:

- String branch() - возвращает имя ветки
- Event.Type type()- возвращает тип событий (COMMIT или MERGE)
- List<Event> caughtEvents() - возвращает список событий, перехваченных этим WebHook
- void onEvent(Event event) - способ отправки события в этот веб-перехватчик


Класс [GitRepoObservers](src/ua/hillel/observer/git/GitRepoObservers.java):

- Repository newRepository() - создает репозиторий c одной веткой "main" и возвращает репозиторий
- WebHook mergeToBranchWebHook(String branchName) - возвращает WebHook, который отслеживает события слияния для переданной ветки
- WebHook commitToBranchWebHook(String branchName) - возвращает WebHook, который отслеживает события создания коммитов для переданной ветки