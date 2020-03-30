export interface IUserProfileData {
  id?: number;
  active?: boolean;
  pgDataCode?: string;
  userProfileId?: number;
}

export class UserProfileData implements IUserProfileData {
  constructor(public id?: number, public active?: boolean, public pgDataCode?: string, public userProfileId?: number) {
    this.active = this.active || false;
  }
}
