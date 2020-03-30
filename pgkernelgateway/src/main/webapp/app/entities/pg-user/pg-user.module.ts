import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgUserComponent,
  PgUserDetailComponent,
  PgUserUpdateComponent,
  PgUserDeletePopupComponent,
  PgUserDeleteDialogComponent,
  pgUserRoute,
  pgUserPopupRoute
} from './';

const ENTITY_STATES = [...pgUserRoute, ...pgUserPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PgUserComponent, PgUserDetailComponent, PgUserUpdateComponent, PgUserDeleteDialogComponent, PgUserDeletePopupComponent],
  entryComponents: [PgUserComponent, PgUserUpdateComponent, PgUserDeleteDialogComponent, PgUserDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgUserModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
