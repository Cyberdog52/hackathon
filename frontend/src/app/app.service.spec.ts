import { AppService } from './app.service';
import { UserInfoDto } from 'src/model/UserInfoDto';
import { BadgeDto } from 'src/model/BadgeDto';
import { SkillDto } from 'src/model/SkillDto';
import { of } from 'rxjs';

describe('AppService', () => {
  let httpClientSpy: { get: jasmine.Spy };
  let appService: AppService;
  const API_USER_BASE_PATH = 'api/user/';

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    appService = new AppService(httpClientSpy as any);
  });

  it('should be created', () => {
    expect(appService).toBeTruthy();
  });

  describe('getUserInfo', () => {
    const API_USER_SEARCH_PATH = '/search';

    it('should return expected userinfo', (done: DoneFn) => {
      const userId = 'mrom';
      const userInfoData: UserInfoDto = {
        id: userId,
        firstName: 'Marco',
        lastName: 'Romanutti',
        jobTitle: 'Advanced Software Engineer',
        pictureId: '23320',
      };

      httpClientSpy.get.and.returnValue(of(userInfoData));

      const response$ = appService.getUserInfo(userId);
      response$.subscribe((resData) => {
        expect(resData).toEqual(userInfoData, 'expected userinfo');
        done();
      }, done.fail);

      expect(httpClientSpy.get).toHaveBeenCalledOnceWith(
        API_USER_BASE_PATH + userId + API_USER_SEARCH_PATH
      );
    });
  });

  describe('getBadges', () => {
    const API_USER_BADGES_PATH = '/badges';

    it('should return expected badges', (done: DoneFn) => {
      const userId = 'mrom';
      const badgeData: BadgeDto[] = [
        {
          iconId: 'hackathon',
          description: 'Zuehlke Hackathony',
        },
      ];

      httpClientSpy.get.and.returnValue(of(badgeData));

      const response$ = appService.getBadges(userId);
      response$.subscribe((resData) => {
        expect(resData).toEqual(badgeData, 'expected badges');
        done();
      }, done.fail);

      expect(httpClientSpy.get).toHaveBeenCalledOnceWith(
        API_USER_BASE_PATH + userId + API_USER_BADGES_PATH
      );
    });
  });

  describe('getSkills', () => {
    const API_USER_SKILLS_PATH = '/skills';

    it('should return expected badges', (done: DoneFn) => {
      const userId = 'mrom';
      const skillsData: SkillDto[] = [
        {
          skillId: 89,
          name: 'JavaScript',
          description:
            'JavaScript (JS) is a lightweight interpreted or JIT-compiled programming language with first-class functions.',
          pictureId: 'skill89',
          rank: 1,
        },
      ];

      httpClientSpy.get.and.returnValue(of(skillsData));

      const response$ = appService.getSkills(userId);
      response$.subscribe((resData) => {
        expect(resData).toEqual(skillsData, 'expected skills');
        done();
      }, done.fail);

      expect(httpClientSpy.get).toHaveBeenCalledOnceWith(
        API_USER_BASE_PATH + userId + API_USER_SKILLS_PATH
      );
    });
  });
});
