import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PersonTypeComponent,
  PersonTypeDetailComponent,
  PersonTypeUpdateComponent,
  PersonTypeDeletePopupComponent,
  PersonTypeDeleteDialogComponent,
  personTypeRoute,
  personTypePopupRoute
} from './';

const ENTITY_STATES = [...personTypeRoute, ...personTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonTypeComponent,
    PersonTypeDetailComponent,
    PersonTypeUpdateComponent,
    PersonTypeDeleteDialogComponent,
    PersonTypeDeletePopupComponent
  ],
  entryComponents: [PersonTypeComponent, PersonTypeUpdateComponent, PersonTypeDeleteDialogComponent, PersonTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPersonTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
