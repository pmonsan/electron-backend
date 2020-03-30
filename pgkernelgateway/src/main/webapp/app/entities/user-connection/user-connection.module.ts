import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  UserConnectionComponent,
  UserConnectionDetailComponent,
  UserConnectionUpdateComponent,
  UserConnectionDeletePopupComponent,
  UserConnectionDeleteDialogComponent,
  userConnectionRoute,
  userConnectionPopupRoute
} from './';

const ENTITY_STATES = [...userConnectionRoute, ...userConnectionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserConnectionComponent,
    UserConnectionDetailComponent,
    UserConnectionUpdateComponent,
    UserConnectionDeleteDialogComponent,
    UserConnectionDeletePopupComponent
  ],
  entryComponents: [
    UserConnectionComponent,
    UserConnectionUpdateComponent,
    UserConnectionDeleteDialogComponent,
    UserConnectionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayUserConnectionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
