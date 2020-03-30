/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgModuleComponent } from 'app/entities/pg-module/pg-module.component';
import { PgModuleService } from 'app/entities/pg-module/pg-module.service';
import { PgModule } from 'app/shared/model/pg-module.model';

describe('Component Tests', () => {
  describe('PgModule Management Component', () => {
    let comp: PgModuleComponent;
    let fixture: ComponentFixture<PgModuleComponent>;
    let service: PgModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgModuleComponent],
        providers: []
      })
        .overrideTemplate(PgModuleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgModuleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgModuleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgModule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgModules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
