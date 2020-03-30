import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ForexComponent,
  ForexDetailComponent,
  ForexUpdateComponent,
  ForexDeletePopupComponent,
  ForexDeleteDialogComponent,
  forexRoute,
  forexPopupRoute
} from './';

const ENTITY_STATES = [...forexRoute, ...forexPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ForexComponent, ForexDetailComponent, ForexUpdateComponent, ForexDeleteDialogComponent, ForexDeletePopupComponent],
  entryComponents: [ForexComponent, ForexUpdateComponent, ForexDeleteDialogComponent, ForexDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayForexModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
