/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitTypeComponent } from 'app/entities/limit-type/limit-type.component';
import { LimitTypeService } from 'app/entities/limit-type/limit-type.service';
import { LimitType } from 'app/shared/model/limit-type.model';

describe('Component Tests', () => {
  describe('LimitType Management Component', () => {
    let comp: LimitTypeComponent;
    let fixture: ComponentFixture<LimitTypeComponent>;
    let service: LimitTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitTypeComponent],
        providers: []
      })
        .overrideTemplate(LimitTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LimitTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LimitTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LimitType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.limitTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
