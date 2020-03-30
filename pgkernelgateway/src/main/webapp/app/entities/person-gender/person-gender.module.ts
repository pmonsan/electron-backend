import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PersonGenderComponent,
  PersonGenderDetailComponent,
  PersonGenderUpdateComponent,
  PersonGenderDeletePopupComponent,
  PersonGenderDeleteDialogComponent,
  personGenderRoute,
  personGenderPopupRoute
} from './';

const ENTITY_STATES = [...personGenderRoute, ...personGenderPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonGenderComponent,
    PersonGenderDetailComponent,
    PersonGenderUpdateComponent,
    PersonGenderDeleteDialogComponent,
    PersonGenderDeletePopupComponent
  ],
  entryComponents: [
    PersonGenderComponent,
    PersonGenderUpdateComponent,
    PersonGenderDeleteDialogComponent,
    PersonGenderDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPersonGenderModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
