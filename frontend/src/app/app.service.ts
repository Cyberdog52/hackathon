import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfoDto } from 'src/model/UserInfoDto';
import { BadgeDto } from 'src/model/BadgeDto';
import { SkillDto } from 'src/model/SkillDto';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  private readonly API_USER_BASE_PATH = 'api/user/';
  private readonly API_USER_SEARCH_PATH = '/search';
  private readonly API_USER_BADGES_PATH = '/badges';
  private readonly API_USER_SKILLS_PATH = '/skills';

  constructor(private http: HttpClient) {}

  getUserInfo(term: string): Observable<UserInfoDto> {
    return this.http.get<UserInfoDto>(
      this.API_USER_BASE_PATH + term + this.API_USER_SEARCH_PATH
    );
  }

  getBadges(userId: string): Observable<BadgeDto[]> {
    return this.http.get<BadgeDto[]>(
      this.API_USER_BASE_PATH + userId + this.API_USER_BADGES_PATH
    );
  }

  getSkills(userId: string): Observable<SkillDto[]> {
    return this.http.get<SkillDto[]>(
      this.API_USER_BASE_PATH + userId + this.API_USER_SKILLS_PATH
    );
  }
}
