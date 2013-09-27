namespace java tmp.thrift.gen

struct LoginResult
{
	1:bool result,
	2:string description
}
struct WorldPlayer
{
	1:string session,
	2:i64 userId,
	3:string roleUUID,
	4:string roleName,
	5:string state,
	6:i32 level,
	7:string jobType,
	8:string ip
}
struct PageData
{
	1:i32 totalCount,
	2:i32 pageSize,
	3:i32 currentPage,
	4:list<WorldPlayer> dataList
}
service GmService
{
  LoginResult login(1:string user, 2:string password),
  void logout(1:string session),
  void tick(1:string session, 2:string roleName),
  void getOnlinePlayers(1:string session, 2:i32 pageNo, 3:i32 pageSize),
  void sendMail(1:string session, 2:string roleName, 3:i32 itemTemplateId, 4:i32 number),
  void shutdownServer(1:string session),
  void sendNotice(1:string session, 2:string notice)
}