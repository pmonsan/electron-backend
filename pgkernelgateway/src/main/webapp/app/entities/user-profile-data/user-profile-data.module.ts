import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  UserProfileDataComponent,
  UserProfileDataDetailComponent,
  UserProfileDataUpdateComponent,
  UserProfileDataDeletePopupComponent,
  UserProfileDataDeleteDialogComponent,
  userProfileDataRoute,
  userProfileDataPopupRoute
} from './';

const ENTITY_STATES = [...userProfileDataRoute, ...userProfileDataPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserProfileDataComponent,
    UserProfileDataDetailComponent,
    UserProfileDataUpdateComponent,
    UserProfileDataDeleteDialogComponent,
    UserProfileDataDeletePopupComponent
  ],
  entryComponents: [
    UserProfileDataComponent,
    UserProfileDataUpdateComponent,
    UserProfileDataDeleteDialogComponent,
    UserProfileDataDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayUserProfileDataModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
